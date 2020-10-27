package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.servlet.DatabaseUtils.*;

public class QueryServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private QueryServlet servlet;

    @Before
    public void init() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        servlet = new QueryServlet();
        DatabaseUtils.clear();
    }

    @After
    public void afterTest() throws SQLException {
        DatabaseUtils.clear();
    }


    @Test
    public void maxInEmptyTable() throws Exception {
        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("<h1>Product with max price: </h1>\n"), result);
    }

    @Test
    public void maxFromTreeElements() throws Exception {
        when(request.getParameter("command")).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        add("name1",10);
        add("name2",40);
        add("name3",30);

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("<h1>Product with max price: </h1>\nname2\t40</br>\n"), result);
    }


    @Test
    public void minEmptyTable() throws Exception {
        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("<h1>Product with min price: </h1>\n"), result);
    }

    @Test
    public void minFromTreeElements() throws Exception {
        when(request.getParameter("command")).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        add("name1",40);
        add("name2",10);
        add("name3",30);

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("<h1>Product with min price: </h1>\nname2\t10</br>\n"), result);
    }

    @Test
    public void sumEmptyTable() throws Exception {
        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("Summary price: \n0\n"), result);
    }

    @Test
    public void sumFromTreeElements() throws Exception {
        when(request.getParameter("command")).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        add("name1",40);
        add("name2",10);
        add("name3",30);

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("Summary price: \n80\n"), result);
    }

    @Test
    public void countEmptyTable() throws Exception {
        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("Number of products: \n0\n"), result);
    }

    @Test
    public void countFromTreeElements() throws Exception {
        when(request.getParameter("command")).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        add("name1",40);
        add("name2",10);
        add("name3",30);

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult("Number of products: \n3\n"), result);
    }


    static String wrapResult(String res) {
        return START_HTML_WRAPPER + res + END_HTML_WRAPPER;
    }
}