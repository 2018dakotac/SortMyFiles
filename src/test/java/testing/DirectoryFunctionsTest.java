package testing;

import SortMyFiles.DirectoryFunctions;
import SortMyFiles.FileDatabase;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertTrue;

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
    static FileDatabase testdb;
    static DirectoryFunctions test;
    @BeforeClass
    public static void createStuff(){
        testdb = new FileDatabase();
        testdb.deleteDefaultTable();
        testdb.createDefaultTable();

        test = new DirectoryFunctions();

    }
    @AfterClass
    public static void finalCleanUp() {
        //testdb.deleteDefaultTable();
        try {
            test.deleteDirectory(new File("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest"));
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    @Before
    public void init(){

        //make some test directories
        test.createDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2");
        test.createDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest","tempDirTest2");
        assertTrue(makeFile("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2\\text2.txt"));
        assertTrue(makeFile("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\text1.txt"));
        testdb.insertFile("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\text1.txt");
        testdb.insertFile("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2\\text2.txt");

    }
    @After
    public void cleanUp(){
        //shouldnt need cleanup
    }

    @Test
    public void fullDirectoryFuncTest(){
        assertTrue(testdb.inDatabase("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\text1.txt"));
        assertTrue(testdb.inDatabase("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1\\1\\2\\text2.txt"));
        testdb.printTable();
        test.moveDirectory("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\temp\\DirTest1","SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2");
        testdb.printTable();
        assertTrue(testdb.inDatabase("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\text1.txt"));
        assertTrue(testdb.inDatabase("SAMPLE SORTING DIRECTORY\\directoryFunctionsTest\\tempDirTest2\\DirTest1\\1\\2\\text2.txt"));

    }

}
