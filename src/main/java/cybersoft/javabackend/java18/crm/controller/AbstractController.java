package cybersoft.javabackend.java18.crm.controller;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AbstractController extends HttpServlet {
    private final Gson gson = new Gson();

    protected void responseJson(HttpServletResponse resp, Object object) throws IOException {
        String json = gson.toJson(object);
        PrintWriter out = resp.getWriter();
        out.println(json);
        out.flush();
    }

    protected String getJsonFromRequest(HttpServletRequest req) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = "";
        BufferedReader br = new BufferedReader(req.getReader());
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
