package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.StatusModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository extends AbstractRepository<StatusModel> {

    public List<StatusModel> findAll() {
        final String query = """
                select id, name from status
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<StatusModel> statusModels = new ArrayList<>();
            while (resultSet.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(resultSet.getInt("id"));
                statusModel.setName(resultSet.getString("name"));
                statusModels.add(statusModel);
            }
            return statusModels;
        });
    }
}
