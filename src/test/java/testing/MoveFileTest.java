package testing;

import SortMyFiles.DirectoryFunctions;
import SortMyFiles.FileDatabase;
import SortMyFiles.MoveFile;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MoveFileTest {

    private boolean  makeFile(String path){
        File file = new File(path);

        boolean fileCreated = false;

            try {
            fileCreated = file.createNewFile();
        }
            catch (
        IOException ioe) {
            System.out.println("Error while creating empty file: " + ioe);
        }
        return fileCreated;
    }
    //a function to return absolute path to allow database testing on any machine
    private String aP(String path){
        File file = new File(path);
        return file.getAbsolutePath();


    }
    static FileDatabase testdb;
    static MoveFile test;
    static DirectoryFunctions dirFunc;
    @BeforeClass
    public static void createStuff(){
        testdb = new FileDatabase();
        testdb.DELETEWHOLETABLE();
        testdb.createDefaultTable();
        test = new MoveFile();
        dirFunc = new DirectoryFunctions();

    }
    @AfterClass
    public static void finalCleanUp() {

        try {
            dirFunc.deleteDirectory("SAMPLE SORTING DIRECTORY");
        }catch(IOException e){
            e.printStackTrace();
        }
        testdb.DELETEWHOLETABLE();
    }
    @Before
    public void init(){

        //make some test directories
        dirFunc.createDirectory("SAMPLE SORTING DIRECTORY\\MoveFile\\something");
        assertTrue(makeFile("SAMPLE SORTING DIRECTORY\\MoveFile\\text1.txt"));
        testdb.insertFile(aP("SAMPLE SORTING DIRECTORY\\MoveFile\\text1.txt"));
        testdb.addTag(aP("SAMPLE SORTING DIRECTORY\\MoveFile\\text1.txt"),"mercy",2);
    }
    @After
    public void cleanUp(){
        try{
            dirFunc.deleteDirectory("SAMPLE SORTING DIRECTORY\\MoveFile");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void fullMoveFileTest(){
        //tests creating moving and copying a directory and then deleting it all
        // while making sure the database is properly updated
        try{
            assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\MoveFile\\text1.txt")));
            test.moveFile("SAMPLE SORTING DIRECTORY\\MoveFile\\text1.txt","SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text1.txt");
            testdb.printTable();
            assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text1.txt")));
            testdb.addTag("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text1.txt","mercy",3);
            ArrayList<String> testList = testdb.findTag("mercy");
            assertEquals("database out of sync",aP("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text1.txt"),testList.get(0));
            test.copyFile("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text1.txt","SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text2.txt");
            testdb.printTable();
            testList = testdb.findTag("mercy");
            assertEquals("database out of sync",aP("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text1.txt"),testList.get(0));
            assertEquals("database out of sync",aP("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text2.txt"),testList.get(1));
            testdb.printTable();
            test.deleteFile("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text1.txt");
            assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text2.txt")));
            assertFalse(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\MoveFile\\something\\text1.txt")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
