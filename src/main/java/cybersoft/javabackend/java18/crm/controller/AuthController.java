package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.service.AuthService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "authController", urlPatterns = UrlUtils.LOGIN)
public class AuthController extends AbstractController {
    private AuthService authService;

    private Gson gson;

    @Override
    public void init() throws ServletException {
        authService = AuthService.getINSTANCE();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = getJsonFromRequest(req);
        UserModel userModel = gson.fromJson(json, UserModel.class);
        userModel = authService.processLogin(userModel.getEmail(), userModel.getPassword());
        responseJson(resp, userModel);
    }
}
