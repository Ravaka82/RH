package controller;

import java.io.IOException;
import java.util.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.*;
import model.*;

public class LoginController extends SpeHttpServlet{

    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Inserable b=(Inserable)instanciation(req.getParameter("classeName"));
        RequestDispatcher rsD=null;
        fromReqToModele(req, b);
        
        // managing depending on if the infomrmations are existing or no
        LoginService ls=new LoginService();
        if(ls.isCorrectIdentifier(b))
        {
            System.out.println("correct");
            // rsD=req.getRequestDispatcher("errorLogin.jsp");
        }

        else
        {
            System.out.println("not correct");
            // rsD=req.getRequestDispatcher("accueil.jsp");
        }

        // rsD.forward(req,resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
    }
}