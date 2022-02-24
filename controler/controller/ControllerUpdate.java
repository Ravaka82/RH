package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.ServiceUpdate;
import model.Inserable;

public class ControllerUpdate extends SpeHttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //    redirection any amin ny formulaire d'insertion
        req.setAttribute("id", req.getParameter("id"));

        RequestDispatcher rsD=req.getRequestDispatcher("update"+req.getParameter("classeName")+".jsp");
        rsD.forward(req, resp);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String primaryKey=req.getParameter("id");
        String classeName=req.getParameter("classeName");

        Inserable b=(Inserable)instanciation(classeName);
        fromReqToModele(req, b);

        ServiceUpdate itsService=new ServiceUpdate();
        try {
            itsService.modify(b,primaryKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
