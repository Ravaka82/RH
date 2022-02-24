package controller;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Inserable;


public class ControllerInsert extends SpeHttpServlet{
    public ControllerInsert(){

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Inserable b=(Inserable)instanciation(req.getParameter("classeName"));
        RequestDispatcher rsD=null;
        
        fromReqToModele(req, b);        
        b.create(b);

        // req.setAttribute("liste", b.read((Inserable)instanciation(req.getParameter("classeName"))));
        // RequestDispatcher rsD=req.getRequestDispatcher("lister.jsp");
        // rsD.forward(req, resp);
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}
