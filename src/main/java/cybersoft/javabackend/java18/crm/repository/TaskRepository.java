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
                order by j.id asc, t.name asc
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
                select id, name, start_date, end_date, user_id, job_id
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
                set name = ?, start_date = ?, end_date = ?, user_id= ?, job_id = ?
                where id = ?
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, taskModel.getName());
            statement.setString(2, taskModel.getStartDate());
            statement.setString(3, taskModel.getEndDate());
            statement.setInt(4, taskModel.getUserId());
            statement.setInt(5, taskModel.getJobId());
            statement.setInt(6, taskModel.getId());
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

    // use for user profile
    public List<TaskModel> findTaskByUser(String userId) {
        final String query = """
                select t.id, t.name, j.name as job, t.start_date, t.end_date, s.name as status, s.id as status_id
                from tasks t, jobs j, users u, status s
                where t.job_id = j.id and t.user_id = u.id and t.status_id = s.id
                and u.id = ?
                order by j.id asc
                 """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<TaskModel> taskModels = new ArrayList<>();
            while (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(resultSet.getInt("id"));
                taskModel.setName(resultSet.getString("name"));
                taskModel.setJobName(resultSet.getString("job"));
                taskModel.setStartDate(resultSet.getString("start_date"));
                taskModel.setEndDate(resultSet.getString("end_date"));
                taskModel.setStatusName(resultSet.getString("status"));
                taskModel.setStatusId(resultSet.getInt("status_id"));
                taskModels.add(taskModel);
            }
            return taskModels;
        });
    }

    public int updateProfile(TaskModel taskModel) {
        final String query = """
                update tasks set status_id = ?
                where id = ?
                """;
        return executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, taskModel.getStatusId());
            statement.setInt(2, taskModel.getId());
            return statement.executeUpdate();
        });
    }

    // user for user-details
    public List<TaskModel> getUserDetails(String userId) {
        final String query = """
                select u.id, t.name, t.start_date, t.end_date, s.id as status_id, j.name as job
                from users u, tasks t, status s, jobs j
                where u.id = t.user_id and s.id = t.status_id and j.id = t.job_id and u.id = ?
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<TaskModel> taskModels = new ArrayList<>();
            while (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setUserId(resultSet.getInt("id"));
                taskModel.setName(resultSet.getString("name"));
                taskModel.setStartDate(resultSet.getString("start_date"));
                taskModel.setEndDate(resultSet.getString("end_date"));
                taskModel.setStatusId(resultSet.getInt("status_id"));
                taskModel.setJobName(resultSet.getString("job"));
                taskModels.add(taskModel);
            }
            return taskModels;
        });
    }

    // user for groupwork-details
    public List<TaskModel> getUserParticipateJob(String jobId) {
        final String query = """
                select u.id, u.fullname
                from users u, jobs j, tasks t
                where u.id = t.user_id and j.id = t.job_id and j.id = ?
                group by u.id
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, jobId);
            ResultSet resultSet = statement.executeQuery();
            List<TaskModel> taskModels = new ArrayList<>();
            while (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setUserId(resultSet.getInt("id"));
                taskModel.setFullName(resultSet.getString("fullname"));
                taskModels.add(taskModel);
            }
            return taskModels;
        });
    }

    // user for groupwork-details
    public List<TaskModel> getUserTaskByJobId(String jobId, String userId) {
        final String query = """
                select u.id as user_id, t.name, s.id as status_id
                from tasks t, jobs j, users u, status s
                where t.job_id = j.id and t.user_id = u.id and t.status_id = s.id
                and j.id = ? and u.id = ?
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, jobId);
            statement.setString(2, userId);
            ResultSet resultSet = statement.executeQuery();
            List<TaskModel> taskModels = new ArrayList<>();
            while (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setUserId(resultSet.getInt("user_id"));
                taskModel.setName(resultSet.getString("name"));
                taskModel.setStatusId(resultSet.getInt("status_id"));
                taskModels.add(taskModel);
            }
            return taskModels;
        });
    }
}
