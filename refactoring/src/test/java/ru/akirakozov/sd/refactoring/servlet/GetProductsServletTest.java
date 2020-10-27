package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.servlet.DatabaseUtils.wrapResult;

public class GetProductsServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private GetProductsServlet servlet;

    @Before
    public void init() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        servlet = new GetProductsServlet();
        DatabaseUtils.clear();
    }

    @After
    public void afterTest() throws SQLException {
        DatabaseUtils.clear();
    }

    @Test
    public void getFromEmptyTable() throws IOException {
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();
        Assert.assertEquals(wrapResult(""), result);
    }

    @Test
    public void getFromTableWithTreeProducts() throws IOException, SQLException {
        DatabaseUtils.add("name1", 1);
        DatabaseUtils.add("name2", 2);
        DatabaseUtils.add("name3", 3);

        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("name1\t1</br>\n" + "name2\t2</br>\n" + "name3\t3</br>\n"), result);
    }

}