package config;


import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SCC-COMLAB
 */
public class db_configuration {
    
    private Connection connection;
    
    public db_configuration(){
        try{
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/milktea_db", "root", "");
            }catch(SQLException e){
                System.err.println("Cannot connect to database: " + e.getMessage());
            }
        
        
    }
    
    public ResultSet getData(String sql) throws SQLException {
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public void insertData(String sql){
        try{
        PreparedStatement pstmt=connection.prepareStatement(sql);
        
        pstmt.executeUpdate();
        System.out.println("Inserted Successfully!");
        pstmt.close();
       }catch(SQLException e){
            System.out.println("Connection Error: "+e);
    }
    }
    
    public void deleteData(int id){
        try{
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM tbl_milktea WHERE m_id=?");
            stmt.setInt(1,id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0){
               System.out.println(rowsDeleted +"rows were deleted.");                
            }else{
                System.out.println("No wos were deleted.");
            }
            stmt.close();
            connection.close();         
        }catch(SQLException e){
            System.out.println("Error deleting data:" +e.getMessage());
        }   
    }
    
    public void deletedData(int id){
        try{
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM tbl_addstock WHERE a_id=?");
            stmt.setInt(1,id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0){
               System.out.println(rowsDeleted +"rows were deleted.");                
            }else{
                System.out.println("No wos were deleted.");
            }
            stmt.close();
            connection.close();         
        }catch(SQLException e){
            System.out.println("Error deleting data:" +e.getMessage());
        }   
    }
    
    public int updateData(String sql){
      int num = 0;
      try{
          String query = sql;
          PreparedStatement pstmt = connection.prepareStatement(query);
          int rowsUpdated = pstmt.executeUpdate();
          if(rowsUpdated >0){
              System.out.println("DATA UPDATED SUCCESFULLY!");
              num = 1;                    
      }else{
              System.out.println("DATA UPDATED FAILED!");
              num = 0;
          }
          
      }catch (SQLException ex){
          ex.printStackTrace();
    }
     return num;
}

    public void DeleteData(int id){
        try{
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM tbl_cashier WHERE c_id=?");
            stmt.setInt(1,id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0){
               System.out.println(rowsDeleted +"rows were deleted.");                
            }else{
                System.out.println("No wos were deleted.");
            }
            stmt.close();
            connection.close();         
        }catch(SQLException e){
            System.out.println("Error deleting data:" +e.getMessage());
        }   
    }
    
}


    



    

