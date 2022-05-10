package com.vttp.miniproject.Project.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // cast
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        // get the http session
        HttpSession session = httpReq.getSession();

        // check for authentication
        String username = (String) session.getAttribute("username");
        if ((username == null) || username.isBlank()) {
            httpResp.sendRedirect("/");
        }

        // pass to next filter
        chain.doFilter(request, response);
    }
}
