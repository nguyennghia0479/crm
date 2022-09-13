package cybersoft.javabackend.java18.crm.model;

import lombok.Data;

import java.util.List;

@Data
public class UserModel {
    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private String avatar;
    private Integer roleId;
    private String roleName;
    private List<TaskModel> taskModels;
}
