package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.TaskModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends AbstractRepository<TaskModel> {

    public List<TaskModel> findAll() {
        final String query = """
                select t.id, t.name, j.name as job, u.fullname, t.start_date, t.end_date, s.name as status
                from tasks t, jobs j, users u, status s
                where t.job_id = j.id and t.user_id = u.id and t.status_id = s.id
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<TaskModel> taskModels = new ArrayList<>();
            while (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setName(resultSet.getString("name"));
                taskModel.setJobName(resultSet.getString("job"));
                taskModel.setFullName(resultSet.getString("fullname"));
                taskModel.setStartDate(resultSet.getString("start_date"));
                taskModel.setEndDate(resultSet.getString("end_date"));
                taskModel.setStatusName(resultSet.getString("status"));
                taskModels.add(taskModel);
            }
            return taskModels;
        });
    }

    public TaskModel findTaskById(String id) {
        final String query = """
                select id, name, start_date, end_date, user_id, job_id, status_id
                from tasks where id = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setName(resultSet.getString("name"));
                taskModel.setStartDate(resultSet.getString("start_date"));
                taskModel.setEndDate(resultSet.getString("end_date"));
                taskModel.setUserId(resultSet.getInt("user_id"));
                taskModel.setJobId(resultSet.getInt("job_id"));
                taskModel.setStatusId(resultSet.getInt("status_id"));
                return taskModel;
            }
            return null;
        });
    }

    public int saveTask(TaskModel taskModel) {
        final String query = """
                insert into tasks(name, start_date, end_date, user_id, job_id, status_id)
                values(?, ?, ?, ?, ?, ?)
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, taskModel.getName());
            statement.setString(2, taskModel.getStartDate());
            statement.setString(3, taskModel.getEndDate());
            statement.setInt(4, taskModel.getUserId());
            statement.setInt(5, taskModel.getJobId());
            statement.setInt(6, taskModel.getStatusId());
            return statement.executeUpdate();
        });
    }

    public int updateTask(TaskModel taskModel) {
        final String query = """
                update tasks
                set name = ?, start_date = ?, end_date = ?, user_id= ?, job_id = ?, status_id = ?
                where id = ?
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, taskModel.getName());
            statement.setString(2, taskModel.getStartDate());
            statement.setString(3, taskModel.getEndDate());
            statement.setInt(4, taskModel.getUserId());
            statement.setInt(5, taskModel.getJobId());
            statement.setInt(6, taskModel.getStatusId());
            statement.setInt(7, taskModel.getId());
            return statement.executeUpdate();
        });
    }

    public int deleteTaskById(String id) {
        final String query = """
                delete from tasks where id = ?
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        });
    }
}
