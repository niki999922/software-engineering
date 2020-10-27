package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.servlet.process.GetProductsProcess;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = new GetProductsProcess().process();
        response.getWriter().println(result);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
