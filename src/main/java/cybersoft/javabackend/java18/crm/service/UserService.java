package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.UserRepository;

import java.util.List;

public class UserService {
    private static UserService INSTANCE = null;
    private final UserRepository userRepository;

    private UserService() {
        userRepository = new UserRepository();
    }

    public static UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findUserById(String id) {
        return userRepository.findById(id);
    }

    public int saveAndUpdateUser(UserModel userModel) {
        if (userModel.getId() == null) {
            return userRepository.saveUser(userModel);
        } else {
            return userRepository.updateUser(userModel);
        }
    }

    public int deleteUserById(String id) {
        return userRepository.deleteUserById(id);
    }
}
