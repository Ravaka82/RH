package model;

import java.lang.reflect.*;
import java.math.BigDecimal;


public class Inserable {
   public Object getValues(String attribute)
   {
        String cat="get"+attribute.substring(0,1).toUpperCase()+attribute.substring(1).toLowerCase();

        Method m1=null;
        try {
            m1=this.getClass().getDeclaredMethod(cat);
            return m1.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("ts tafa le getValues");
        return null;
         
   }

   public void setValue(String attribute,Object valueArray) throws Exception
    {       
        try {
            Method[] allMethods=this.getClass().getDeclaredMethods();
            Method m1=null;

            String cat="set"+attribute.substring(0,1).toUpperCase()+attribute.substring(1).toLowerCase();
            // System.out.println(cat);

            int i;
            for (i = 0; i < allMethods.length; i++) {
                if(cat.equals(allMethods[i].getName()))
                {   
                    m1=this.getClass().getDeclaredMethod(cat,allMethods[i].getParameterTypes());
                    break;
                }
            }

            // System.out.println("Le type de la variable venat de la base "+valueArray.getClass().getSimpleName()); 
            try {
                m1.invoke(this,allMethods[i].getParameterTypes()[0].cast(valueArray));
            } catch (ClassCastException c) {
                System.out.println("Casting manuel excecute"); 

                if(valueArray instanceof BigDecimal)
                    m1.invoke(this, Double.parseDouble(((BigDecimal)valueArray).toString()));

                if(valueArray instanceof String && !allMethods[i].getParameterTypes()[0].equals(String.class))
                    m1.invoke(this, stringToAdequate((String)valueArray, allMethods[i].getParameterTypes()[0]));

            }catch(ArrayIndexOutOfBoundsException aiob){
                aiob.printStackTrace();
                throw new Exception("La methode cat "+cat+" non repertorie");
            }
            
           } catch (Exception e) {
               e.printStackTrace();
               throw new Exception("For a field "+attribute+" who has the values "+valueArray+" the set value didn't work");
           }
    }


   public void create(Inserable i) {
        LinkedSql liS=new LinkedSql();
        liS.insert(i, i.getClass().getSimpleName() , "yes");

        try {
            liS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }

   public Inserable[] read(Inserable i){
       LinkedSql liS=new LinkedSql();
       return liS.select(i, i.getClass().getSimpleName());
   }

   public void update(Inserable o,String primaryKey) throws Exception{
       LinkedSql liS=new LinkedSql();
       liS.update(o, o.getClass().getSimpleName(), primaryKey);
   }

    public Object stringToAdequate(String valueArray, Class c) throws Exception {
        try {
            if(c.equals(Integer.class))
                return Integer.parseInt(valueArray);

            else if(c.equals(double.class))
                return Double.parseDouble(valueArray);

            // the date is another problem
                
        } catch (Exception e) {
            throw new Exception("Impossible de caster "+valueArray+" to "+c.getSimpleName());
        } 

        return null;
    }

    
}
