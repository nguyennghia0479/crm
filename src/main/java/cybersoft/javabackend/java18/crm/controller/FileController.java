package cybersoft.javabackend.java18.crm.controller;

import cybersoft.javabackend.java18.crm.model.ResponseData;
import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.service.UserService;
import cybersoft.javabackend.java18.crm.utils.DirUtils;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(name = "fileController", urlPatterns = {
        UrlUtils.URL_UPLOAD_FILE,
        UrlUtils.URL_DOWNLOAD_FILE,
        UrlUtils.URL_GET_USER_ID
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100)
public class FileController extends AbstractController {
    private UserService userService;

    private ResponseData responseData;

    private String id;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        responseData = new ResponseData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = userService.downloadAvatar(req.getParameter("id"));
        resp.setContentType("image/jpeg");

        try (ServletOutputStream outputStream = resp.getOutputStream();
             FileInputStream file = new FileInputStream(userModel.getAvatar());
             BufferedInputStream input = new BufferedInputStream(file);
             BufferedOutputStream output = new BufferedOutputStream(outputStream)) {
            int chars = 0;
            while ((chars = input.read()) != -1) {
                output.write(chars);     // display image
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals(UrlUtils.URL_GET_USER_ID)) {
            this.id = req.getParameter("id");
        } else {
            uploadImg(req, resp);
        }
    }

    private void uploadImg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                List<FileItem> multipart = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
                for (FileItem item : multipart) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        String path = DirUtils.UPLOAD_DIRECTORY + File.separator + name;
                        if (!userService.isAvatarExists(name)) {
                            item.write(new File(DirUtils.UPLOAD_DIRECTORY + File.separator + name));
                        }
                        userService.saveAvatar(this.id, path);
                    }
                }
                responseData.getResponseData(1, "The file uploaded successfully.");
            } catch (Exception ex) {
                responseData.getResponseData(0, "File Upload Failed");
            }
        } else {
            responseData.getResponseData(0, "Sorry this Servlet only handles file upload request");
        }
        responseJson(resp, responseData);
    }
}
