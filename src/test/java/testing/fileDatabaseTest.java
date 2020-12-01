package testing;

import SortMyFiles.FileDatabase;
import org.junit.*;


import org.junit.runners.MethodSorters;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.DEFAULT)
public class fileDatabaseTest {
    static FileDatabase test;
    @BeforeClass
    public static void createStuff(){
        test = new FileDatabase();
        test.deleteDefaultTable();
        test.createDefaultTable();
    }
    @AfterClass
    public static void cleanUP() {
        test.deleteDefaultTable();
    }
    @Before
    public void init(){
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test1.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test2.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test3.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test4.txt");
    }
    @After
    public void cleanTable(){
        test.deleteDefaultTable();
        test.createDefaultTable();
    }

    @Test
    public void tagTest(){
        test.addTag("D:\\SAMPLE SORTING DIRECTORY\\test1.txt","yeet",1);
        test.addTag("D:\\SAMPLE SORTING DIRECTORY\\test2.txt","yeet",2);
        test.addTag("D:\\SAMPLE SORTING DIRECTORY\\test3.txt","yeet",3);
        ArrayList<String> testList = test.findTag("yeet");
        assertEquals("array lists are not the same","D:\\SAMPLE SORTING DIRECTORY\\test1.txt",testList.get(0));
        assertEquals("array lists are not the same","D:\\SAMPLE SORTING DIRECTORY\\test2.txt",testList.get(1));
        assertEquals("array lists are not the same","D:\\SAMPLE SORTING DIRECTORY\\test3.txt",testList.get(2));
    }
    @Test
    public void tableManagementTest(){
        assertTrue(test.updateFile("D:\\SAMPLE SORTING DIRECTORY\\test4.txt","D:\\SAMPLE SORTING DIRECTORY\\FAKENAME.txt"));
        assertTrue(test.deleteFile("D:\\SAMPLE SORTING DIRECTORY\\FAKENAME.txt"));
        assertTrue(test.deleteFile("D:\\SAMPLE SORTING DIRECTORY\\test2.txt"));


    }




}