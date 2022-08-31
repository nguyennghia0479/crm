package cybersoft.javabackend.java18.crm.model;

import lombok.Data;

@Data
public class UserModel {
    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private String avatar;
    private Integer roleId;
    private String roleName;
}
