package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.RoleModel;
import cybersoft.javabackend.java18.crm.repository.RoleRepository;

import java.util.List;

public class RoleService {
    private static RoleService INSTANCE = null;
    private final RoleRepository roleRepository;

    private RoleService() {
        this.roleRepository = new RoleRepository();
    }

    public static RoleService getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new RoleService();
        return INSTANCE;
    }

    public List<RoleModel> findAll() {
        return roleRepository.findAll();
    }

    public RoleModel findRoleById(String id) {
        return roleRepository.findRoleById(id);
    }

    public int deleteRoleById(String id) {
        return roleRepository.deleteRoleById(id);
    }

    public int saveAndUpdateRole(RoleModel roleModel) {
        if (roleModel.getId() == null)
            return roleRepository.saveRole(roleModel);
        else
            return roleRepository.updateRole(roleModel);
    }
}
