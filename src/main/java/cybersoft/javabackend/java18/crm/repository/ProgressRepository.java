package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.ProgressModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgressRepository extends AbstractRepository<ProgressModel> {

    public ProgressModel findProgressForProfileByUserId(String userId) {
        final String query = """
                select count(*) as amount,
                    (select count(*)
                        from tasks t, users u, status s
                        where t.user_id = u.id and t.status_id = s.id and u.id = ? and s.id = 1) as backlog,
                    (select count(*)
                        from tasks t, users u, status s
                        where t.user_id = u.id and t.status_id = s.id and u.id = ? and s.id = 2) as in_progress,
                    (select count(*)
                        from tasks t, users u, status s
                        where t.user_id = u.id and t.status_id = s.id and u.id = ? and s.id = 3) as done
                from tasks t, users u
                where t.user_id = u.id and u.id = ?;
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            statement.setString(2, userId);
            statement.setString(3, userId);
            statement.setString(4, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getProgressModel(resultSet);
            }
            return null;
        });
    }

    public ProgressModel findProgressForJobByJobId(String jobId) {
        final String query = """
                select count(*) as amount,
                    (select count(*)
                        from tasks t, jobs j, status s
                        where t.job_id = j.id and t.status_id = s.id and j.id = ? and s.id = 1) as backlog,
                    (select count(*)
                        from tasks t, jobs j, status s
                        where t.job_id = j.id and t.status_id = s.id and j.id = ? and s.id = 2) as in_progress,
                    (select count(*)
                        from tasks t, jobs j, status s
                        where t.job_id = j.id and t.status_id = s.id and j.id = ? and s.id = 3) as done
                from tasks t, jobs j
                where t.job_id = j.id and j.id = ?;
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, jobId);
            statement.setString(2, jobId);
            statement.setString(3, jobId);
            statement.setString(4, jobId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getProgressModel(resultSet);
            }
            return null;
        });
    }

    public ProgressModel findProgressForAllTask() {
        final String query = """
                select count(*) as amount,
                    (select count(*)
                        from tasks t, status s
                        where t.status_id = s.id and s.id = 1) as backlog,
                    (select count(*)
                        from tasks t, status s
                        where t.status_id = s.id and s.id = 2) as in_progress,
                    (select count(*)
                        from tasks t, status s
                        where t.status_id = s.id and s.id = 3) as done
                from tasks t, status s
                where t.status_id = s.id;
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getProgressModel(resultSet);
            }
            return null;
        });
    }

    private ProgressModel getProgressModel(ResultSet resultSet) throws SQLException {
        ProgressModel progressModel = new ProgressModel();
        progressModel.setAmount(resultSet.getInt("amount"));
        progressModel.setBackLog(resultSet.getInt("backlog"));
        progressModel.setInProgress(resultSet.getInt("in_progress"));
        progressModel.setDone(resultSet.getInt("done"));
        return progressModel;
    }
}
