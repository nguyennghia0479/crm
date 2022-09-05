package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.repository.TaskRepository;

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
}
