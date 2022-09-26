package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.RoleModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository extends AbstractRepository<RoleModel> {

    public List<RoleModel> findAll() {
        final String query = """
                select id, name, description from roles
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<RoleModel> roleModels = new ArrayList<>();
            while (resultSet.next()) {
                RoleModel roleModel = new RoleModel()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"));
                roleModels.add(roleModel);
            }
            return roleModels;
        });
    }

    public RoleModel findRoleById(String id) {
        final String query = """
                select id, name, description
                from roles
                where id = ?    
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new RoleModel()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"));
            }
            return null;
        });
    }

    public int deleteRoleById(String id) {
        final String query = """
                delete from roles where id = ?            
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        });
    }

    public int updateRole(RoleModel roleModel) {
        final String query = """
                update roles set name = ?, description = ?
                where id = ?            
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roleModel.getName());
            statement.setString(2, roleModel.getDescription());
            statement.setInt(3, roleModel.getId());
            return statement.executeUpdate();
        });
    }

    public int saveRole(RoleModel roleModel) {
        final String query = """
                insert into roles(name, description)
                values(?, ?)            
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roleModel.getName());
            statement.setString(2, roleModel.getDescription());
            return statement.executeUpdate();
        });
    }
}
