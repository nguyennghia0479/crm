package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.UserRepository;

public class AuthService {
    private static AuthService INSTANCE = null;

    private final UserRepository userRepository;

    private AuthService() {
        userRepository = new UserRepository();
    }

    public static AuthService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new AuthService();
        }
        return INSTANCE;
    }

    public UserModel processLogin(String email, String password) {
        UserModel userModel = userRepository.getUserByEmail(email);
        if (userModel == null) {
            return null;
        }
        if (userModel.getPassword().equals(password)) {
            return userModel;
        }
        return null;
    }
}
