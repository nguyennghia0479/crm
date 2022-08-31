package cybersoft.javabackend.java18.crm.controller;

import cybersoft.javabackend.java18.crm.jdbc.MySQLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/demo")
public class Demo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = MySQLConnection.getConnection()) {
            resp.getWriter().append("OK");
        } catch (SQLException e) {
            resp.getWriter().append("NOT OK");
            throw new RuntimeException(e.getMessage());
        }
    }
}
