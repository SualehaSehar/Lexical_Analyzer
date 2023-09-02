/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical_analyzer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lexical_analyzer.helper.Attributes;

public class SQL {

    private static SQL sql = null;
    
     private SQL() {
        
    }
    
    public static SQL getSqlInstance(){
        if(sql == null){sql = new SQL();}
        return sql;
    } 
    
    public Connection createConnection(){
        //new Attributes().exp_op = a;
        //a = new Attributes().exp_op;
        Connection con = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection("jdbc:ucanaccess://semantic.accdb");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
    // save
    public static void executeUpdate(String query) throws Exception{
        Connection conn = null;
        PreparedStatement pst = null;
        try{
            conn = SQL.getSqlInstance().createConnection();
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(query);
            pst.executeUpdate();
            pst.close();
            
            conn.commit();
            conn.setAutoCommit(true);
        }
        catch(Exception e){
            conn.rollback();
            //throw new RuntimeException();
            e.printStackTrace();
        }
        finally{
            if (conn!=null) conn.close();
            if (pst!=null) pst.close();      
        }
    }
    
    // read
    public static ResultSet execute(String query) throws Exception{
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null; 
        try{
            conn = SQL.getSqlInstance().createConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            pst.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (conn!=null) conn.close();
            if (pst!=null) pst.close();      
        }
       return rs;
    }
    
    public static void delete(String query) throws Exception{
        Connection conn = null;
        PreparedStatement pst = null; 
        try{
            conn = SQL.getSqlInstance().createConnection();
            pst = conn.prepareStatement(query);
            pst.executeUpdate();
            pst.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (conn!=null) conn.close();
            if (pst!=null) pst.close();      
        }
    }
    
}
