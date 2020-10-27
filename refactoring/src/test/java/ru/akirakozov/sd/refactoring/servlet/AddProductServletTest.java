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

public class AddProductServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private AddProductServlet servlet;

    @Before
    public void init() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        servlet = new AddProductServlet();
        DatabaseUtils.clear();
    }

    @After
    public void afterTest() throws SQLException {
        DatabaseUtils.clear();
    }

    @Test
    public void testNameAndPrice() throws Exception {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn("10");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String result = stringWriter.getBuffer().toString();
        Assert.assertEquals("OK\n", result);
    }

    @Test
    public void testNullNameAndPrice() throws IOException {
        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("price")).thenReturn("10");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String result = stringWriter.getBuffer().toString();
        Assert.assertEquals("OK\n", result);
    }

    @Test
    public void testEmptyNameAndPrice() throws IOException {
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("price")).thenReturn("10");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String result = stringWriter.getBuffer().toString();
        Assert.assertEquals("OK\n", result);
    }

    @Test(expected = NumberFormatException.class)
    public void testNameAndNullPrice() throws IOException {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
    }

    @Test(expected = NumberFormatException.class)
    public void testNameAndEmptyPrice() throws IOException {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn("");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
    }
}