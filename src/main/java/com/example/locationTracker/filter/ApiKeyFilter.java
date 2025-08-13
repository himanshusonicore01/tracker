package com.example.locationTracker.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class ApiKeyFilter implements Filter {

    @Value("${security.api-key}")
    private String expectedKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Allow health + docs without key if you want
        String path = req.getRequestURI();
        if (path.startsWith("/actuator") || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        String apiKey = req.getHeader("X-API-Key");
        if (expectedKey.equals(apiKey)) {
            chain.doFilter(request, response);
        } else {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Unauthorized: invalid API key");
        }
    }
}