package cybersoft.javabackend.java18.crm.model;

import lombok.Data;

@Data
public class ProgressModel {
    private int amount;
    private int backLog;
    private int inProgress;
    private int done;
}
