package model;

import java.sql.*;

public class LinkedSql extends Linked {
    public LinkedSql() {
        connect();
    }

    @Override
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/rh", "postgres", "indro");
            this.stmt = this.conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    public void close() throws Exception {
        if(getRs()!=null)
            getRs().close();
        
        if(getStmt()!=null)
            getStmt().close();

        if(getConn()!=null)
            getConn().close();
}


}