package cybersoft.javabackend.java18.crm.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcExecute<T> {

    T processQuery(Connection connection) throws SQLException;
}
