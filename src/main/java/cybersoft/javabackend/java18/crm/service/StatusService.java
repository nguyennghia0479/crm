package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.StatusModel;
import cybersoft.javabackend.java18.crm.repository.StatusRepository;

import java.util.List;

public class StatusService {
    private static StatusService INSTANCE = null;
    private final StatusRepository statusRepository;

    private StatusService() {
        statusRepository = new StatusRepository();
    }

    public static StatusService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new StatusService();
        }
        return INSTANCE;
    }

    public List<StatusModel> findAll() {
        return statusRepository.findAll();
    }
}
