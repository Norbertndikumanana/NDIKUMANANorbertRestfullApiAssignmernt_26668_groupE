package com.auca.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (name == null || email == null || password == null
                || name.trim().isEmpty() || email.trim().isEmpty()
                || password.length() < 6) {
            req.setAttribute("error", "Invalid input");
            req.getRequestDispatcher("/index.html").forward(req, resp);
            return;
        }

        // Minimal server-side storage: put user info into session
        HttpSession session = req.getSession(true);
        session.setAttribute("userName", name);
        session.setAttribute("userEmail", email);

        // Redirect to the static home page (which reads localStorage). Server-side apps
        // would typically forward to a JSP or set cookies instead.
        resp.sendRedirect(req.getContextPath() + "/home.html");
    }
}
