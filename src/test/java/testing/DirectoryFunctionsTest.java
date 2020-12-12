package testing;

import com.SortMyFiles.DirectoryFunctions;
import com.SortMyFiles.FileDatabase;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DirectoryFunctionsTest {

private boolean  makeFile(String path){
    File file = new File(path);

    boolean fileCreated = false;

        try {
        fileCreated = file.createNewFile();
    }
        catch (IOException ioe) {
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
    static DirectoryFunctions test;
    @BeforeClass
    public static void createStuff(){
        testdb = new FileDatabase();
        testdb.DELETEWHOLETABLE();
        testdb.createDefaultTable();

        test = new DirectoryFunctions();

    }
    @AfterClass
    public static void finalCleanUp() {

        try {
            test.deleteDirectory("SAMPLE SORTING DIRECTORY");
        }catch(IOException e){
            e.printStackTrace();
        }
        testdb.DELETEWHOLETABLE();
    }
    @Before
    public void init(){

        //make some test directories
        try {
            test.createDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2");
            test.createDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest", "tempDirTest2");
        }catch(IOException e){
            e.printStackTrace();
        }
        assertTrue(makeFile("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2\\text2.txt"));
        assertTrue(makeFile("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\text1.txt"));
        testdb.insertFile(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\text1.txt"));
        testdb.insertFile(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2\\text2.txt"));
        testdb.addTag(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2\\text2.txt"),"2",2);
    }
    @After
    public void cleanUp(){
        try{
            test.deleteDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void fullDirectoryFuncTest(){
        //tests creating moving and copying a directory and then deleting it all
        // while making sure the database is properly updated
        try{
        assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\text1.txt")));
        assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2\\text2.txt")));

        test.moveDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1","SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2");
        testdb.printTable();
        assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\text1.txt")));
        assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\2\\text2.txt")));
        ArrayList<String> testList = testdb.findTag("2");
        assertEquals("database out of sync",aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\2\\text2.txt"),testList.get(0));
        test.deleteDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\2");
        testdb.printTable();
        assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\text1.txt")));
        assertFalse(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\2\\text2.txt")));
        test.copyDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest","SAMPLE SORTING DIRECTORY\\yeet");
        testdb.printTable();
        assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\text1.txt")));
        assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\yeet\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\text1.txt")));
        test.deleteDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest");
        assertFalse(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\text1.txt")));
        assertTrue(testdb.inDatabase(aP("SAMPLE SORTING DIRECTORY\\yeet\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\text1.txt")));
        testdb.printTable();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
