package model;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public abstract class Linked {
    Connection conn=null;
    String password;
    String user;
    Statement stmt=null;
    ResultSet rs=null;
    ResultSetMetaData rsmd=null;
    String[] allTableName;
    String actualUsedTable;
    Object[][] allInfos;
    String affichage;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Connection getConn() {
        return conn;
    }
    
    public void setConn(Connection conn) {
        this.conn = conn;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public ResultSetMetaData getRsmd() {
        return rsmd;
    }

    public void setRsmd(ResultSetMetaData rsmd) {
        this.rsmd = rsmd;
    }

    // for select
    public void sqlRequest(String request)
    {
        try {
            this.rs=this.stmt.executeQuery(request);
            this.rsmd=this.rs.getMetaData();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // for INSERT, UPDATE, or DELETE
    public void sqlRequestNoRs(String request){
        try {
            this.stmt.executeUpdate(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object instanciation(String classeName)
    {
        Object o=null;

        Class bref;
        try {
            bref=Class.forName(classeName);
            o=bref.getDeclaredConstructor().newInstance();
            return o;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public abstract void connect();

    public void insert(Inserable o, String tableName,String primaryKey) {
        String sql = "insert into " + tableName + " values (";

        try {
            sqlRequest("select * from " + tableName);
            int i = 1;

            if(primaryKey!=null)
            {
                // if it was oracle
                // sql+="'"+tableName+"'||s_"+tableName+".nextVal,";

                // if it's postgres
                sql+="default,";

                i=2;
            }

            for ( ;i <= this.rsmd.getColumnCount(); i++) {

                sql+=adaptingJavaTypeToSql(o, i);

                if(i!=this.rsmd.getColumnCount())
                    sql+=",";
            }
            sql += ")";
            System.out.println("The request for insert is"+sql);
            this.stmt.executeUpdate(sql);
            // this.conn.commit();

        } catch (Exception e) {
            System.out.println("table not found :"+tableName);
            e.printStackTrace();
        }
    }

    public void update(Inserable objetReference,String tableName,String primaryKey) throws Exception 
    {
        try {
            sqlRequest("select * from "+tableName);
        int columnNumber=rsmd.getColumnCount();

        String sql="update "+tableName+" set ";
        String commaOrNo=",";

        for (int i = 1; i <=columnNumber; i++) {
            if(i == columnNumber)
                commaOrNo="";

            if(rsmd.getColumnName(i).contains("id"))
                continue;

            sql+=rsmd.getColumnName(i)+'='+adaptingJavaTypeToSql(objetReference, i)+commaOrNo;
            
        }

        sql+=" where id="+primaryKey+"";
        System.out.println("La requete pour update "+sql);

        // samihafa ny exec update sy ny executeQuery 
        sqlRequestNoRs(sql);
        } catch (Exception e) {
            throw new Exception("Erreur sur une requete de update, en plus detailler :"+e.getMessage());
        }
                
    }

    public String checkConditionMultipleField(String sql,Field[] fields,String[] fieldsMajuscule,Inserable[] o)
    {
        
        for (int i = 0; i < o.length; i++) {
            sql=testConditionField(sql, fields, fieldsMajuscule, o[i]);

            if(i<o.length-1)
                sql+=" or ";
        }

        return sql;
    }

    public String testConditionField(String sql,Field[] fields,String[] fieldsMajuscule,Inserable o)
    {
        int flag=0;
        String equalsOrLike="";
        for (int i = 0; i < fields.length; i++) {
            fieldsMajuscule[i]=fields[i].getName().substring(0,1).toUpperCase()+fields[i].getName().substring(1).toLowerCase();
            // System.out.println("Le nom de l'attribut change="+fieldsMajuscule[i]);
            
            if(o.getValues(fieldsMajuscule[i])==null)
                continue;
            
            if(o.getValues(fieldsMajuscule[i]) instanceof Integer && (int)o.getValues(fieldsMajuscule[i])==0)      
                continue;

            else if(o.getValues(fieldsMajuscule[i]) instanceof Double && (double)o.getValues(fieldsMajuscule[i])==0.0)
                continue;

            if(fieldsMajuscule[i].contains("Id"))
                equalsOrLike=fieldsMajuscule[i]+"='"+o.getValues(fieldsMajuscule[i])+"'";

            else
                equalsOrLike=searchIfType(o.getValues(fieldsMajuscule[i]),fieldsMajuscule[i]);

            if(flag==0)
            {
                if(sql.contains("where"))
                {
                    sql+=equalsOrLike;
                    continue;
                }

                
                sql+=" where "+equalsOrLike;
                flag=1;
                continue;
            }

            sql+=" and "+equalsOrLike;
        }
        return sql;
    }

    public Inserable[] select(Inserable o,String tableName)
    {
        String requete="select * from "+tableName;

        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldsMajuscule=new String[fields.length];
        

        requete=testConditionField(requete,fields,fieldsMajuscule,o);
        System.out.println("La requete etant "+requete);
        sqlRequest(requete);

        ArrayList<Inserable> tempResult=new ArrayList<Inserable>();
        int i,j;
        int columnIndex;
        
        try {
            for(i=0;this.rs.next();i++) {
                tempResult.add((Inserable)instanciation(o.getClass().getName()));

                // normal oue midepasse le izy satria normalement rehefa manao dérnière incrémentation le columnindex de tokony hijanona le boucle nefa izy ts caper 
                    // amin ny nombre de colonne fa amin ny fields length
                for (j = 0,columnIndex=1; j < fields.length && columnIndex<=this.rsmd.getColumnCount(); j++) {
                    try {
                        if(rsmd.getColumnName(columnIndex).equalsIgnoreCase(fields[j].getName()   )) {
                            // System.out.println("For a field "+fieldsMajuscule[j]+" = value as "+fields[j].getName());
                            tempResult.get(i).setValue(fieldsMajuscule[j],rs.getObject(columnIndex) );   
                            columnIndex++;
                        }
                    } catch (SQLException e) {
                        System.out.println("Sur une erreur de matchin entre indice de sql indice et java indice j="+j+" et columnIndex"+columnIndex);
                        e.printStackTrace();
                    }

                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();    
        }

        return tempResult.toArray(new Inserable[0]);
    }


    public Inserable[] select(Inserable[] o,String tableName)
    {
        String sql="select * from "+tableName;

        Field[] fields=o[0].getClass().getDeclaredFields();
        String[] fieldsMajuscule=new String[fields.length];
        
        sql=checkConditionMultipleField(sql,fields,fieldsMajuscule,o);
        System.out.println("la requete etant "+sql);
        sqlRequest(sql);

        ArrayList<Inserable> tempResult=new ArrayList<Inserable>();
        int i;
        try {
            for(i=0;this.rs.next();i++) {
                tempResult.add((Inserable)instanciation(o[0].getClass().getName()));
                for (int j = 0; j < fields.length; j++) {
                    // System.out.println("La valeur a entrer "+rs.getObject(j+1));
                    tempResult.get(i).setValue(fieldsMajuscule[j],rs.getObject(j+1) );   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();    
        }

        return tempResult.toArray(new Inserable[0]);
    }

    public String searchIfType(Object o,String fi)
    {
        if(o instanceof String)
            return fi+" like '%"+o+"%'";

        if(o instanceof Date){
            Calendar cal=Calendar.getInstance();
            cal.setTime((Date)o);
            String calIntegral=cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH);
            return fi+"='"+calIntegral+"'";
        }

        return fi+"="+o;
    }

    public String adaptingJavaTypeToSql(Inserable o,int i)
    {
        String sql="";
        try {
            // System.out.println("getting the value of "+this.rsmd.getColumnName(i)+" which is "+o.getValues(this.rsmd.getColumnName(i)));
            if (o.getValues(this.rsmd.getColumnName(i)) instanceof String)                                    
                    sql= "'" + o.getValues(this.rsmd.getColumnName(i)) + "'";

                else if (o.getValues(this.rsmd.getColumnName(i)) instanceof Date){
                    Calendar cal=Calendar.getInstance();
                    cal.setTime((Date)o.getValues(this.rsmd.getColumnName(i)));
                    int month=cal.get(Calendar.MONTH)+1;
                    sql="TO_DATE('"+cal.get(Calendar.YEAR)+"-"+month+"-"+cal.get(Calendar.DAY_OF_MONTH)+"')";
                }                

                else
                    sql+= o.getValues(this.rsmd.getColumnName(i));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sql;
    }
    
}