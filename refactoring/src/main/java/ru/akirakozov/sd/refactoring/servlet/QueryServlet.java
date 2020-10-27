package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.servlet.process.CommandHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    CommandHandler commandHandler;

    public QueryServlet() {
        commandHandler = new CommandHandler();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        String result = commandHandler.getProcess(command).process();
        response.getWriter().println(result);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
