package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.UserRepository;
import cybersoft.javabackend.java18.crm.utils.DirUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

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
        return userRepository.findUserById(id);
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

    public List<UserModel> getUserToSelect() {
        return userRepository.getUserToSelect();
    }

    public void saveAvatar(String userId, String path) {
        userRepository.saveAvatar(userId, path);
    }

    public UserModel downloadAvatar(String userId) {
        return userRepository.downloadAvatar(userId);
    }

    public boolean isAvatarExists(String name) {
        final File folder = new File(DirUtils.UPLOAD_DIRECTORY);
        return listFilesForFolder(folder, name);
    }

    private boolean listFilesForFolder(final File folder, String name) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, name);
            } else {
                if (fileEntry.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
}
