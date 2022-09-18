package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.ResponseData;
import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.service.UserService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "userController", urlPatterns = {
        UrlUtils.URL_USER,
        UrlUtils.URL_SELECT_USER
//        "/user-table.html"
})
public class UserController extends AbstractController {
    private UserService userService;

    private Gson gson;

    private ResponseData responseData;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        gson = new Gson();
        responseData = new ResponseData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals(UrlUtils.URL_USER)) {
            String id = req.getParameter("id");
            if (id == null) {
                List<UserModel> userModels = userService.findAll();
                responseJson(resp, userModels);
            } else {
                UserModel userModel = userService.findUserById(id);
                responseJson(resp, userModel);
            }
        } else {
            List<UserModel> userModels = userService.getUserToSelect();
            responseJson(resp, userModels);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqJson = getJsonFromRequest(req);
        UserModel userModel = gson.fromJson(reqJson, UserModel.class);
        int result = userService.saveAndUpdateUser(userModel);
        String message;
        if (result == 1)
            message = "Add new user successfully";
        else
            message = "Add new user failed";
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqJson = getJsonFromRequest(req);
        UserModel userModel = gson.fromJson(reqJson, UserModel.class);
        int result = userService.saveAndUpdateUser(userModel);
        String message;
        if (result == 1)
            message = "Update user successfully";
        else
            message = "Update user failed";
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = userService.deleteUserById(id);
        String message;
        if (result == 1)
            message = "Delete user successfully";
        else
            message = "Delete user failed";
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }
}
