package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.StatusModel;
import cybersoft.javabackend.java18.crm.service.StatusService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "statusController", urlPatterns = {
        UrlUtils.URL_STATUS
})
public class StatusController extends HttpServlet {
    private StatusService statusService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        statusService = StatusService.getINSTANCE();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StatusModel> statusModels = statusService.findAll();
        String json = gson.toJson(statusModels);
        PrintWriter out = resp.getWriter();
        out.println(json);
        out.flush();
    }
}
