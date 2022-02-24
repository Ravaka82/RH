package controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import model.Inserable;
import java.util.ArrayList;

public class SpeHttpServlet extends HttpServlet{
    public Object instanciation(String classeName)
    {
        Object o=null;

        Class bref;
        try {
            bref=Class.forName("model."+classeName);
            o=bref.getDeclaredConstructor().newInstance();
            return o;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
    
    public void fromReqToModele(HttpServletRequest requete,Inserable b){
        // hard to iterate through an enum
        ArrayList<String> allInfos=new ArrayList<String>(requete.getParameterMap().keySet());

        for (int j = 0; j < allInfos.size(); j++) {
            if(allInfos.get(j).equals("classeName"))
                continue;

            try {
                b.setValue(allInfos.get(j), requete.getParameter(allInfos.get(j)));
            } catch (Exception e) {
                requete.setAttribute("actualError", e);
            }
        }

    }
}
