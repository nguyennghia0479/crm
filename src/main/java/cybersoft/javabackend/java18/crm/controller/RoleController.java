package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.ResponseData;
import cybersoft.javabackend.java18.crm.model.RoleModel;
import cybersoft.javabackend.java18.crm.service.RoleService;
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

// api/role -> Get
@WebServlet(name = "roleController", urlPatterns = {
        UrlUtils.URL_ROLE
})
public class RoleController extends HttpServlet {
    private final Gson gson = new Gson();
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        roleService = RoleService.getINSTANCE();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            List<RoleModel> roleModels = roleService.findAll();
            printJson(resp, roleModels);
        } else {
            RoleModel roleModel = roleService.findRoleById(id);
            printJson(resp, roleModel);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String json = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String reqJson = getJsonFromRequest(req);
        RoleModel roleModel = new Gson().fromJson(reqJson, RoleModel.class);
        int result = roleService.saveAndUpdateRole(roleModel);
        String message;
        if (result == 1) {
            message = "Added new role successful";
        } else {
            message = "Added new role failed";
        }
        ResponseData responseData = new ResponseData().getResponseData(result, message);
        printJson(resp, responseData);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = roleService.deleteRoleById(id);
        String message;
        if (result == 1) {
            message = "Deleted role successful";
        } else {
            message = "Deleted role failed";
        }
        ResponseData responseData = new ResponseData().getResponseData(result, message);
        printJson(resp, responseData);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqJson = getJsonFromRequest(req);
        RoleModel roleModel = new Gson().fromJson(reqJson, RoleModel.class);
        int result = roleService.saveAndUpdateRole(roleModel);
        String message;
        if (result == 1) {
            message = "Updated role successful";
        } else {
            message = "Updated role failed";
        }
        ResponseData responseData = new ResponseData().getResponseData(result, message);
        printJson(resp, responseData);
    }

    private void printJson(HttpServletResponse resp, Object object) throws IOException {
        String respJson = this.gson.toJson(object);
        PrintWriter out = resp.getWriter();
        out.println(respJson);
        out.flush();
    }

    private String getJsonFromRequest(HttpServletRequest req) throws IOException {
        BufferedReader br = new BufferedReader(req.getReader());
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
