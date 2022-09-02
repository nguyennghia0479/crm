package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.JobModel;
import cybersoft.javabackend.java18.crm.repository.JobRepository;

import java.util.List;

public class JobService {
    private static JobService INSTANCE = null;
    private final JobRepository jobRepository;

    private JobService() {
        jobRepository = new JobRepository();
    }

    public static JobService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JobService();
        }
        return INSTANCE;
    }

    public List<JobModel> findAll() {
        return jobRepository.findAll();
    }

    public JobModel findJobById(String id) {
        return jobRepository.findJobById(id);
    }

    public int saveAndUpdateJob(JobModel jobModel) {
        if (jobModel.getId() == null)
            return jobRepository.saveJob(jobModel);
        else
            return jobRepository.updateJob(jobModel);
    }

    public int deleteJobById(String id) {
        return jobRepository.deleteRoleById(id);
    }
}
