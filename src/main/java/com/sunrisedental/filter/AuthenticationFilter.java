package com.sunrisedental.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(
        urlPatterns = {
                "/dashboard",
                "/appointments",
                "/patients/*",
                "/treatments",
                "/bills",
                "/reports",
                "/users/*"
        }
)
public class AuthenticationFilter
        implements Filter {

    @Override
    public void init(
            final FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest request =
                (HttpServletRequest) servletRequest;

        final HttpServletResponse response =
                (HttpServletResponse) servletResponse;

        final HttpSession session =
                request.getSession(false);

        final boolean loggedIn =
                session != null
                        && session.getAttribute("username") != null;

        if (!loggedIn) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login");

            return;
        }

        filterChain.doFilter(
                request,
                response);
    }

    @Override
    public void destroy() {
    }
}