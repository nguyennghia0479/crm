package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.ProgressModel;
import cybersoft.javabackend.java18.crm.repository.ProgressRepository;

public class ProgressService {
    private static ProgressService INSTANCE = null;
    private final ProgressRepository progressRepository;

    private ProgressService() {
        progressRepository = new ProgressRepository();
    }

    public static ProgressService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ProgressService();
        }
        return INSTANCE;
    }

    public ProgressModel findProgressForProfileByUserId(String userId) {
        return progressRepository.findProgressForProfileByUserId(userId);
    }

    public ProgressModel findProgressForJobByJobId(String jobId) {
        return progressRepository.findProgressForJobByJobId(jobId);
    }

    public ProgressModel findProgressForAllTask() {
        return progressRepository.findProgressForAllTask();
    }
}
