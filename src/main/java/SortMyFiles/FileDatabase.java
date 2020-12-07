package SortMyFiles;

import java.sql.*;
import java.util.ArrayList;
//is it worth making this all static?
/*
NOTE:
must pass absolute paths as strings for guaranteed functionality
 */
public class FileDatabase {

    private static final String DEFAULT_TABLE = "CREATE TABLE IF NOT EXISTS FILEDB " +
            "(path VARCHAR(255) NOT NULL , " +
            " tag1 VARCHAR(255) NOT NULL DEFAULT ''," +
            " tag2 VARCHAR(255) NOT NULL DEFAULT ''," +
            " tag3 VARCHAR(255) NOT NULL DEFAULT ''" +
            ")";
    private static final String h2url = "jdbc:h2:./SMF";
    private static final String username = "sa";
    private static final String password = "";


    public FileDatabase(){
        createDefaultTable();
    }
    /* this function will delete the default database ( NO UNDO) ask if sure before using

     */
    public boolean DELETEWHOLETABLE(){
        String sql = "DROP TABLE FILEDB";
        return sendStatement(sql);
    }
    /*
    this function will create the default table
     */
    public boolean createDefaultTable(){
        return sendStatement(DEFAULT_TABLE);
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
    public boolean insertFile(String filePath){
            String sql = "INSERT INTO FILEDB(path) SELECT '"+filePath+
                    "' FROM DUAL WHERE NOT EXISTS(SELECT path FROM FILEDB " +
                    "WHERE path = '"+filePath+"' LIMIT 1)";
            return sendStatement(sql);

    }
    //will insert but will make duplicates
    public boolean insertFileFast(String filePath){
        String  sql = "INSERT INTO FILEDB VALUES('"+filePath+"','','','')";
        return sendStatement(sql);
    }

    public boolean insertFileFast(String filePath,String tag1,String tag2, String tag3){
        String  sql = "INSERT INTO FILEDB VALUES('"+filePath+"','"+tag1+"','"+tag2+"','"+tag3+"')";
        return sendStatement(sql);
    }


    /*
    this function will copy a database insert if it exists
     */
    public void copyFile(String currentPath,String newPath){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(h2url,username,password);
            stmt = conn.createStatement();
            String query = "SELECT * FROM FILEDB WHERE path = '"+currentPath+"'";
            rs = stmt.executeQuery(query);

            if(rs.next()){
                //kind of inefficient but better readability.
                String tag1 = rs.getString("tag1");
                String tag2 = rs.getString("tag2");
                String tag3 = rs.getString("tag3");
                insertFileFast(newPath,tag1,tag2,tag3);
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
    this function will  update the data base with for a files new location and/or new name if it exists
     */

    public boolean updateFile(String currentPath,String newPath){

            String sql= "UPDATE FILEDB SET path = '"+newPath+"' WHERE path = '"+currentPath+"'";
            return sendStatement(sql);
    }



    /*
    this function will add a tag to the file within the data base for a specific tag column
     */
    public boolean addTag(String filePath, String tag, Integer tagNum){

        String sql;
            if(tagNum ==1) {
                sql = "UPDATE FILEDB " + "SET tag1 = '"+tag+"'" + " WHERE path = '"+filePath+"'";
            }else if(tagNum == 2){
                sql = "UPDATE FILEDB " + "SET tag2 = '"+tag+"'" + " WHERE path = '"+filePath+"'";
            }else{
                sql = "UPDATE FILEDB " + "SET tag3 = '"+tag+"'" + " WHERE path = '"+filePath+"'";
            }
           return sendStatement(sql);
    }

    /*
    this function will delete a file from the table
     */
    public boolean deleteFile(String filePath){
        String sql = "DELETE FROM FILEDB WHERE path = '"+filePath+"'";
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
                result.add(filePath);
                //System.out.println(filePath);
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
   this function will return a files tags in an array list 1 to 3
    */
    public boolean inDatabase(String filePath){
        boolean result = false;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(h2url,username,password);
            stmt = conn.createStatement();
            //String query = "SELECT * FROM FILEDB WHERE tag1 ='"+tag+"' OR tag2 ='"+tag+"' OR tag3 = '"+tag+"'";
            String query = "SELECT * FROM FILEDB WHERE path = '"+filePath+"'";
            rs = stmt.executeQuery(query);

             result = rs.next() ;

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
    this function will return a files tags in an array list 1 to 3
     */
    public boolean hasTag(String filePath,String tag){
        boolean result = false;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(h2url,username,password);
            stmt = conn.createStatement();
            //String query = "SELECT * FROM FILEDB WHERE tag1 ='"+tag+"' OR tag2 ='"+tag+"' OR tag3 = '"+tag+"'";
            String query = "SELECT * FROM FILEDB WHERE path = '"+filePath+"'";
            rs = stmt.executeQuery(query);

            if(rs.next()){//returns true if any of the tags match
                if(rs.getString("tag1")==tag||rs.getString("tag2")==tag||rs.getString("tag3")==tag){
                    result = true;
                }
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
    this function will return a files tags in an array list 1 to 3
     */
    public ArrayList<String> returnTags(String filePath){
        ArrayList<String> result = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(h2url,username,password);
            stmt = conn.createStatement();
            //String query = "SELECT * FROM FILEDB WHERE tag1 ='"+tag+"' OR tag2 ='"+tag+"' OR tag3 = '"+tag+"'";
            String query = "SELECT * FROM FILEDB WHERE path = '"+filePath+"'";
            rs = stmt.executeQuery(query);

            if(rs.next()){
                String tag1 = rs.getString("tag1");
                result.add(tag1);
                String tag2 = rs.getString("tag2");
                result.add(tag2);
                String tag3 = rs.getString("tag3");
                result.add(tag3);
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
                String tag1 = rs.getString("tag1");
                String tag2 = rs.getString("tag2");
                String tag3 = rs.getString("tag3");
                System.out.println("PATH: "+filePath+ " TAG1: "+tag1+" TAG2 "+tag2+" TAG3: " + tag3);
            }
            System.out.println();
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
    this function will return a log recent sorts?
     */
    public ArrayList<String> returnSortLog(){
        ArrayList<String> empty = new ArrayList<>();
        return empty;
    }

//old tests
    ///*
    public static void main(String[] args) {
        FileDatabase test = new FileDatabase();
        test.DELETEWHOLETABLE();
        test.createDefaultTable();
        System.out.println(test.inDatabase("asldkjfla;skjdf"));
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test1.txt");
        System.out.println(test.inDatabase("D:\\SAMPLE SORTING DIRECTORY\\test1.txt"));

        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test2.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test3.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test4.txt");
        test.printTable();
        System.out.println("");
        test.addTag("D:\\SAMPLE SORTING DIRECTORY\\test3.txt","first",1);
        test.findTag("first");
        ArrayList<String> bruh = test.returnTags("D:\\SAMPLE SORTING DIRECTORY\\test3.txt");
        for(String temp: bruh){
            if(!temp.isEmpty())
            System.out.println("return tag:"+temp);
        }
        test.updateFile("D:\\SAMPLE SORTING DIRECTORY\\test3.txt","D:\\SAMPLE SORTING DIRECTORY\\FAKENAME.txt");
        test.updateFile("D:\\SAMPasdfasdfasdfLE SORTING DIRECTORY\\test1.txt","D:\\SAMPLE SORTING DIRECTORY\\FAKENAME.txt");
        //test.updateFileIfExist("D:\\SAMPLE SORTING DIRECTORY\\test3.txt","D:\\SAMPLE SORTING DIRECTORY\\please\\test4.txt");
        test.printTable();
        test.findTag("first");
        bruh = test.returnTags("D:\\SAMPLE SORTING DIRECTORY\\FAKENAME.txt");
        for(String temp: bruh){
            if(!temp.isEmpty())
                System.out.println("return tag:"+temp);
        }
        test.deleteFile("D:\\SAMPLE SORTING DIRECTORY\\FAKENAME.txt");
        System.out.println("");
        test.printTable();
    }
    //*/



}
