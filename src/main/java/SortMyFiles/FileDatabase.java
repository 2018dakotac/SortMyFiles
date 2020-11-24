package SortMyFiles;

import java.sql.*;
import java.util.ArrayList;

public class FileDatabase {
    //TODO: avoid the repetitive try and catch stuff maybe make this a singleton object and have a constant connection?
    //not sure how to deal with closing the connection though as there is no destructor... so will probably leave as is

    //private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DEFAULT_TABLE = "CREATE TABLE IF NOT EXISTS FILEDB " +
            "(path VARCHAR(255) NOT NULL , " +
            " file VARCHAR(255) NOT NULL, " +
            " tag1 VARCHAR(255) NOT NULL DEFAULT ''," +
            " tag2 VARCHAR(255) NOT NULL DEFAULT ''," +
            " tag3 VARCHAR(255) NOT NULL DEFAULT ''" +
            ")";
    private static final String h2url = "jdbc:h2:./SMF";
    private static final String username = "sa";
    private static final String password = "";


    public FileDatabase(){

        // Class.forName(JDBC_DRIVER);
        sendStatement(DEFAULT_TABLE);
    }
    /*
    simple wrapper function to send sql string command to the data base
     */
    private boolean sendStatement(String sql){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(h2url,username,password);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Close connection
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /*
    this function will insert a file's information into the data base if it doesnt already exist

     */
    public boolean insertFile(String filePath, String fileName){

            //this allows duplicates
           // String  sql = "INSERT INTO FILEDB" + "VALUES('"+filePath+"','"+fileName+"','','','')";
            String sql = "INSERT INTO FILEDB(path,file) SELECT '"+filePath+"','"+fileName+
                    "' FROM DUAL WHERE NOT EXISTS(SELECT path FROM FILEDB " +
                    "WHERE path = '"+filePath+"' AND file ='"+fileName+"' LIMIT 1)";
            return sendStatement(sql);

    }


    /*
    this function will copy a file to a new location and update the data base
     */

    public boolean updateFile(String currentPath, String currentName, String newPath, String newName){

            String sql= "UPDATE FILEDB " + "SET path = '"+newPath+"',file = '"+newName+"' WHERE path = '"+currentPath+"' AND file = '"+currentName+"'";
            return sendStatement(sql);
    }

    /*
    this function will add a tag to the file within the data base for a specific tag column
     */
    public boolean addTag(String filePath,String fileName, String tag, Integer tagNum){

        String sql;
            if(tagNum ==1) {
                sql = "UPDATE FILEDB " + "SET tag1 = '"+tag+"'" + " WHERE path = '"+filePath+"' AND file = '"+fileName+"'";
            }else if(tagNum == 2){
                 sql = "UPDATE FILEDB " + "SET tag2 = '"+tag+"'" + " WHERE path = '"+filePath+"' AND file = '"+fileName+"'";
            }else{
                 sql = "UPDATE FILEDB " + "SET tag3 = '"+tag+"'" + " WHERE path = '"+filePath+"' AND file = '"+fileName+"'";
            }
           return sendStatement(sql);
    }

    /*
    this function will return a list of file paths(including file name) with the searched tag
    not sure on return type

     */
    public ArrayList<String> findTag(String tag){
        ArrayList<String> result = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(h2url,username,password);
            stmt = conn.createStatement();
            //String query = "SELECT * FROM FILEDB WHERE tag1 ='"+tag+"' OR tag2 ='"+tag+"' OR tag3 = '"+tag+"'";
            String query = "SELECT * FROM FILEDB WHERE tag1 ='"+tag+"' OR tag2 = '"+tag+"' OR tag3 = '"+tag+"'";
            rs = stmt.executeQuery(query);

            while(rs.next()){
                String filePath = rs.getString("path");
                String fileName = rs.getString("file");
                result.add(filePath+fileName);
                System.out.println(filePath+fileName);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close connection
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
    this prints all contents of the database for easy viewing
     */
    public void printTable(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(h2url,username,password);
            stmt = conn.createStatement();
            String query = "SELECT * FROM FILEDB";
            rs = stmt.executeQuery(query);
            while(rs.next()){
                String filePath = rs.getString("path");
                String fileName = rs.getString("file");
                String tag1 = rs.getString("tag1");
                String tag2 = rs.getString("tag2");
                String tag3 = rs.getString("tag3");
                System.out.println("PATH: "+filePath+ " FILE: "+ fileName+" TAG1: "+tag1+" TAG2 "+tag2+" TAG3: " + tag3);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close connection
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    this function will return a log recent sorts? gh
     */
    public ArrayList<String> returnSortLog(){
        ArrayList<String> empty = new ArrayList<>();
        return empty;
    }




}
