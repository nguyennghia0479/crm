package cybersoft.javabackend.java18.crm.model;

import lombok.Data;

@Data
public class TaskModel {
    private Integer id;
    private String name;
    private String startDate;
    private String endDate;
    private Integer jobId;
    private Integer userId;
    private Integer statusId;
    private String jobName;
    private String fullName;
    private String statusName;
}
