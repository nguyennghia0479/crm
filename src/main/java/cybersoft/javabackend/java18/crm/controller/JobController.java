package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.JobModel;
import cybersoft.javabackend.java18.crm.model.ResponseData;
import cybersoft.javabackend.java18.crm.service.JobService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "jobController", urlPatterns = {
        UrlUtils.URL_JOB,
        UrlUtils.URL_SELECT_JOB
})
public class JobController extends AbstractController {
    private JobService jobService;

    private Gson gson;

    private ResponseData responseData;

    @Override
    public void init() throws ServletException {
        jobService = JobService.getInstance();
        gson = new Gson();
        responseData = new ResponseData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals(UrlUtils.URL_JOB)) {
            String id = req.getParameter("id");
            if (id == null) {
                List<JobModel> jobModels = jobService.findAll();
                responseJson(resp, jobModels);
            } else {
                JobModel jobModel = jobService.findJobById(id);
                responseJson(resp, jobModel);
            }
        } else {
            List<JobModel> jobModels = jobService.getJobToSelect();
            responseJson(resp, jobModels);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = getJsonFromRequest(req);
        JobModel jobModel = gson.fromJson(json, JobModel.class);
        int result = jobService.saveAndUpdateJob(jobModel);
        String message;
        if (result == 1) {
            message = "Add new job successful";
        } else {
            message = "Add new job failed";
        }
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = getJsonFromRequest(req);
        JobModel jobModel = gson.fromJson(json, JobModel.class);
        int result = jobService.saveAndUpdateJob(jobModel);
        String message;
        if (result == 1) {
            message = "Update job successful";
        } else {
            message = "Update job failed";
        }
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = jobService.deleteJobById(id);
        String message;
        if (result == 1)
            message = "Delete job successfully";
        else
            message = "Delete job failed";
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }
}
