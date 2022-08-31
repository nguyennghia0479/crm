package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.ResponseData;
import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.service.UserService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "userController", urlPatterns = {
        UrlUtils.URL_USER
})
public class UserController extends HttpServlet {
    private Gson gson;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            List<UserModel> userModels = userService.findAll();
            printJson(resp, userModels);
        } else {
            UserModel userModel = userService.findUserById(id);
            printJson(resp, userModel);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqJson = getJsonFromRequest(req);
        UserModel userModel = gson.fromJson(reqJson, UserModel.class);
        int result = userService.saveAndUpdateUser(userModel);
        String message;
        if (result == 1)
            message = "Added new user successful";
        else
            message = "Add new user failed";
        ResponseData responseData = new ResponseData().getResponseData(result, message);
        printJson(resp, responseData);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqJson = getJsonFromRequest(req);
        UserModel userModel = gson.fromJson(reqJson, UserModel.class);
        int result = userService.saveAndUpdateUser(userModel);
        String message;
        if (result == 1)
            message = "Updated user successful";
        else
            message = "Updated user failed";
        ResponseData responseData = new ResponseData().getResponseData(result, message);
        printJson(resp, responseData);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = userService.deleteUserById(id);
        String message;
        if (result == 1)
            message = "Deleted user successful";
        else
            message = "Deleted user failed";
        ResponseData responseData = new ResponseData().getResponseData(result, message);
        printJson(resp, responseData);
    }

    private void printJson(HttpServletResponse resp, Object object) throws IOException {
        String json = gson.toJson(object);
        PrintWriter out = resp.getWriter();
        out.println(json);
        out.flush();
    }

    private String getJsonFromRequest(HttpServletRequest req) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(req.getReader());
        String line = "";
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
