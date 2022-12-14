package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends AbstractRepository<UserModel> {

    public List<UserModel> findAll() {
        final String query = """
                select u.id, u.email, u.password, u.fullname, r.name
                from users u, roles r
                where u.role_id = r.id
                order by role_id asc
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<UserModel> userModels = new ArrayList<>();
            while (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setRoleName(resultSet.getString("name"));
                userModels.add(userModel);
            }
            return userModels;
        });
    }

    public UserModel findUserById(String id) {
        final String query = """
                select id, fullname, email, password, role_id
                from users
                where id = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setRoleId(resultSet.getInt("role_id"));
                return userModel;
            }
            return null;
        });
    }

    public int saveUser(UserModel userModel) {
        final String query = """
                insert into users(email, password, fullname, role_id)
                values(?, ?, ?, ?)
                 """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userModel.getEmail());
            statement.setString(2, userModel.getPassword());
            statement.setString(3, userModel.getFullName());
            statement.setInt(4, userModel.getRoleId());
            return statement.executeUpdate();
        });
    }

    public int updateUser(UserModel userModel) {
        final String query = """
                update users
                set email = ?, password = ?, fullname = ?, role_id = ?
                where id = ?
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userModel.getEmail());
            statement.setString(2, userModel.getPassword());
            statement.setString(3, userModel.getFullName());
            statement.setInt(4, userModel.getRoleId());
            statement.setInt(5, userModel.getId());
            return statement.executeUpdate();
        });
    }

    public int deleteUserById(String id) {
        final String query = """
                delete from users where id = ?
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        });
    }

    public List<UserModel> getUserToSelect() {
        final String query = """
                select id, fullname from users            
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<UserModel> userModels = new ArrayList<>();
            while (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModels.add(userModel);
            }
            return userModels;
        });
    }

    public UserModel getUserByEmail(String email) {
        final String query = """
                select u.id, u.fullname, u.email, u.password, r.name as role
                from users u, roles r
                where u.role_id = r.id and u.email = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setRoleName(resultSet.getString("role"));
                return userModel;
            }
            return null;
        });
    }

    public int saveAvatar(String userId, String path) {
        final String query = """
                update users
                set avatar = ?
                where id = ?
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, path);
            statement.setString(2, userId);
            return statement.executeUpdate();
        });
    }

    public UserModel downloadAvatar(String userId) {
        final String query = """
                select avatar from users where id = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setAvatar(resultSet.getString("avatar"));
                return userModel;
            }
            return null;
        });
    }
}
