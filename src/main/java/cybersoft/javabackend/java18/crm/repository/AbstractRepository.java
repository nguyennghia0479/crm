package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.exception.DatabaseNotFoundException;
import cybersoft.javabackend.java18.crm.jdbc.MySQLConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AbstractRepository<T> {
    protected List<T> executeQuery(JdbcExecute<List<T>> process) {
        try (Connection connection = MySQLConnection.getConnection()) {
            return process.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }

    protected T executeQuerySingle(JdbcExecute<T> process) {
        try (Connection connection = MySQLConnection.getConnection()) {
            return process.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }

    protected Integer executeUpdate(JdbcExecute<Integer> process) {
        try (Connection connection = MySQLConnection.getConnection()) {
            return process.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }
}
