package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.*;
import model.*;

public class ControllerListing extends SpeHttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Inserable b=(Inserable)instanciation(req.getParameter("classeName"));

        
        ServiceListing itsService=new ServiceListing();

        req.setAttribute("liste", itsService.listing(b));

        RequestDispatcher rsD=null;
        rsD=req.getRequestDispatcher("lister.jsp");
        rsD.forward(req, resp); 
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}
