package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.JobModel;
import cybersoft.javabackend.java18.crm.model.ResponseData;
import cybersoft.javabackend.java18.crm.service.JobService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;
import cybersoft.javabackend.java18.crm.validation.FormattedDateMatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "jobController", urlPatterns = {
        UrlUtils.URL_JOB
})
public class JobController extends HttpServlet {
    private JobService jobService;
    private Gson gson;

    private FormattedDateMatcher matcher;

    @Override
    public void init() throws ServletException {
        jobService = JobService.getInstance();
        gson = new Gson();
        matcher = new FormattedDateMatcher();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            List<JobModel> jobModels = jobService.findAll();
            responseJson(resp, jobModels);
        } else {
            JobModel jobModel = jobService.findJobById(id);
            responseJson(resp, jobModel);
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
        ResponseData responseData = new ResponseData().getResponseData(result, message);
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
        ResponseData responseData = new ResponseData().getResponseData(result, message);
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
        ResponseData responseData = new ResponseData().getResponseData(result, message);
        responseJson(resp, responseData);
    }

    private void responseJson(HttpServletResponse resp, Object object) throws IOException {
        String json = gson.toJson(object);
        PrintWriter out = resp.getWriter();
        out.println(json);
        out.flush();
    }

    private String getJsonFromRequest(HttpServletRequest req) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = "";
        BufferedReader br = new BufferedReader(req.getReader());
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
