package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.ResponseData;
import cybersoft.javabackend.java18.crm.model.RoleModel;
import cybersoft.javabackend.java18.crm.service.RoleService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "roleController", urlPatterns = {
        UrlUtils.URL_ROLE
})
public class RoleController extends AbstractController {
    private RoleService roleService;

    private Gson gson;

    private ResponseData responseData;

    @Override
    public void init() throws ServletException {
        roleService = RoleService.getINSTANCE();
        gson = new Gson();
        responseData = new ResponseData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            List<RoleModel> roleModels = roleService.findAll();
            responseJson(resp, roleModels);
        } else {
            RoleModel roleModel = roleService.findRoleById(id);
            responseJson(resp, roleModel);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String json = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String reqJson = getJsonFromRequest(req);
        RoleModel roleModel = gson.fromJson(reqJson, RoleModel.class);
        int result = roleService.saveAndUpdateRole(roleModel);
        String message;
        if (result == 1) {
            message = "Add new role successfully";
        } else {
            message = "Add new role failed";
        }
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = roleService.deleteRoleById(id);
        String message;
        if (result == 1) {
            message = "Delete role successfully";
        } else {
            message = "Delete role failed";
        }
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqJson = getJsonFromRequest(req);
        RoleModel roleModel = gson.fromJson(reqJson, RoleModel.class);
        int result = roleService.saveAndUpdateRole(roleModel);
        String message;
        if (result == 1) {
            message = "Update role successfully";
        } else {
            message = "Update role failed";
        }
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }
}
