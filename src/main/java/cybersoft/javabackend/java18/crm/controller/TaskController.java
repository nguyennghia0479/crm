package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.ResponseData;
import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.service.TaskService;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "taskController", urlPatterns = {
        UrlUtils.URL_TASK
})
public class TaskController extends AbstractController {
    private TaskService taskService;

    private Gson gson;

    private ResponseData responseData;

    @Override
    public void init() throws ServletException {
        taskService = TaskService.getINSTANCE();
        gson = new Gson();
        responseData = new ResponseData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            List<TaskModel> taskModels = taskService.findAll();
            responseJson(resp, taskModels);
        } else {
            TaskModel taskModel = taskService.findTaskById(id);
            responseJson(resp, taskModel);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = getJsonFromRequest(req);
        TaskModel taskModel = gson.fromJson(json, TaskModel.class);
        taskModel.setStatusId(1);
        int result = taskService.saveAndUpdateTask(taskModel);
        String message;
        if (result == 1)
            message = "Add new task successfully";
        else
            message = "Add new task failed";
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = getJsonFromRequest(req);
        TaskModel taskModel = gson.fromJson(json, TaskModel.class);
        int result = taskService.saveAndUpdateTask(taskModel);
        String message;
        if (result == 1)
            message = "Update task successfully";
        else
            message = "Update task failed";
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = taskService.deleteTaskById(id);
        String message;
        if (result == 1)
            message = "Delete task successfully";
        else
            message = "Delete task failed";
        responseData.getResponseData(result, message);
        responseJson(resp, responseData);
    }
}
