package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private static TaskService INSTANCE = null;
    private final TaskRepository taskRepository;

    private TaskService() {
        taskRepository = new TaskRepository();
    }

    public static TaskService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new TaskService();
        }
        return INSTANCE;
    }

    public List<TaskModel> findAll() {
        return taskRepository.findAll();
    }

    public TaskModel findTaskById(String id) {
        return taskRepository.findTaskById(id);
    }

    public int saveAndUpdateTask(TaskModel taskModel) {
        if (taskModel.getId() == null)
            return taskRepository.saveTask(taskModel);
        else
            return taskRepository.updateTask(taskModel);
    }

    public int deleteTaskById(String id) {
        return taskRepository.deleteTaskById(id);
    }

    public List<TaskModel> findTaskByUser(String userId) {
        return taskRepository.findTaskByUser(userId);
    }

    public int updateProfile(TaskModel taskModel) {
        return taskRepository.updateProfile(taskModel);
    }

    public List<TaskModel> getUserDetails(String userId) {
        return taskRepository.getUserDetails(userId);
    }

    public List<UserModel> getJobDetailsById(String jobId) {
        List<UserModel> userModels = new ArrayList<>();
        List<TaskModel> users = taskRepository.getUserParticipateJob(jobId);
        users.forEach(user -> {
            List<TaskModel> tasks = taskRepository.getUserTaskByJobId(jobId, String.valueOf(user.getUserId()));
            UserModel userModel = new UserModel();
            userModel.setId(user.getUserId());
            userModel.setFullName(user.getFullName());
            userModel.setTaskModels(tasks);
            userModels.add(userModel);
        });
        return userModels;
    }
}
