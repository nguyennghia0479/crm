package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.JobModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobRepository extends AbstractRepository<JobModel> {

    public JobRepository() {
    }

    public List<JobModel> findAll() {
        final String query = """
                select id, name, start_date, end_date from jobs
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<JobModel> jobModels = new ArrayList<>();
            while (resultSet.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStartDate(resultSet.getString("start_date"));
                jobModel.setEndDate(resultSet.getString("end_date"));
                jobModels.add(jobModel);
            }
            return jobModels;
        });
    }

    public JobModel findJobById(String id) {
        final String query = """
                select id, name, start_date, end_date from jobs where id = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStartDate(resultSet.getString("start_date"));
                jobModel.setEndDate(resultSet.getString("end_date"));
                return jobModel;
            }
            return null;
        });
    }

    public int saveJob(JobModel jobModel) {
        final String query = """
                insert into jobs(name, start_date, end_date)
                values(?, ?, ?)
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, jobModel.getName());
            statement.setDate(2, Date.valueOf(jobModel.getStartDate()));
            statement.setDate(3, Date.valueOf(jobModel.getEndDate()));
            return statement.executeUpdate();
        });
    }

    public int updateJob(JobModel jobModel) {
        final String query = """
                update jobs
                set name = ?, start_date = ?, end_date = ?
                where id = ?            
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, jobModel.getName());
            statement.setDate(2, Date.valueOf(jobModel.getStartDate()));
            statement.setDate(3, Date.valueOf(jobModel.getEndDate()));
            statement.setInt(4, jobModel.getId());
            return statement.executeUpdate();
        });
    }

    public int deleteRoleById(String id) {
        final String query = """
                delete from jobs where id = ?
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        });
    }
}
