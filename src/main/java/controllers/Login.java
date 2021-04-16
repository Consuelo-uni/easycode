/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.User;
import dao.UserDAO;
import helper.AppHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jorge
 */
@WebServlet(name = "Login", urlPatterns = {"/login", "/login_validation"})
public class Login extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/login":
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getServletPath()) {
            case "/login_validation":
                String correo = request.getParameter("correo");
                String password = AppHelper.convetirMD5(request.getParameter("password"));

                UserDAO userDao = new UserDAO();

                try {
                    User user = userDao.checkLogin(correo, password);
                    String destPage = "login.jsp";

                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        session.setAttribute("iniciado", true);
                        destPage = "logincorrecto.jsp";
                        response.sendRedirect(AppHelper.baseUrl() + "plataforma");
                    } else {
                        String message = "Datos incorrectos";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    }

                    

                } catch (SQLException | ClassNotFoundException ex) {
                    throw new ServletException(ex);
                }
                break;
        }    
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
