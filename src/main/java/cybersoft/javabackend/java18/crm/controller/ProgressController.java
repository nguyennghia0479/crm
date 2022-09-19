package cybersoft.javabackend.java18.crm.controller;

import cybersoft.javabackend.java18.crm.model.ProgressModel;
import cybersoft.javabackend.java18.crm.service.ProgressService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "progressController", urlPatterns = {
        UrlUtils.URL_PROFILE_PROGRESS,
        UrlUtils.URL_JOB_PROGRESS,
        UrlUtils.URL_TASKS_PROGRESS
})
public class ProgressController extends AbstractController {
    private ProgressService progressService;

    @Override
    public void init() throws ServletException {
        progressService = ProgressService.getINSTANCE();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            ProgressModel progressModel = progressService.findProgressForAllTask();
            System.out.println(progressModel);
            responseJson(resp, progressModel);
        } else {
            if (req.getServletPath().equals(UrlUtils.URL_PROFILE_PROGRESS)) {
                ProgressModel progressModel = progressService.findProgressForProfileByUserId(id);
                responseJson(resp, progressModel);
            } else {
                ProgressModel progressModel = progressService.findProgressForJobByJobId(id);
                responseJson(resp, progressModel);
            }
        }
    }
}
