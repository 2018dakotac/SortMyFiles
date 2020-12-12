package testing;

import com.SortMyFiles.FileDatabase;
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
        test.DELETEWHOLETABLE();
        test.createDefaultTable();
    }
    @AfterClass
    public static void cleanUP() {
        test.DELETEWHOLETABLE();
    }
    @Before
    public void init(){
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test1.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test2.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test3.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test4.txt");
        test.insertFile("D:\\SAMPLE SORTING DIRECTORY\\test4.txt");
    }
    @After
    public void cleanTable(){
        test.DELETEWHOLETABLE();
        test.createDefaultTable();
    }

    @Test
    public void tagTest(){
        test.addTag("D:\\SAMPLE SORTING DIRECTORY\\test1.txt","yeet",1);
        test.addTag("D:\\SAMPLE SORTING DIRECTORY\\test2.txt","yeet",2);
        test.addTag("D:\\SAMPLE SORTING DIRECTORY\\test3.txt","yeet",3);
        test.copyFile("D:\\SAMPLE SORTING DIRECTORY\\test3.txt","D:\\SAMPLE SORTING DIRECTORY\\test5.txt");
        ArrayList<String> testList = test.findTag("yeet");
        assertEquals("database out of sync","D:\\SAMPLE SORTING DIRECTORY\\test1.txt",testList.get(0));
        assertEquals("database out of sync","D:\\SAMPLE SORTING DIRECTORY\\test2.txt",testList.get(1));
        assertEquals("database out of sync","D:\\SAMPLE SORTING DIRECTORY\\test3.txt",testList.get(2));
        assertEquals("database out of sync","D:\\SAMPLE SORTING DIRECTORY\\test5.txt",testList.get(3));
    }
    @Test
    public void tableManagementTest(){
        assertTrue(test.updateFile("D:\\SAMPLE SORTING DIRECTORY\\test4.txt","D:\\SAMPLE SORTING DIRECTORY\\FAKENAME.txt"));
        assertTrue(test.deleteFile("D:\\SAMPLE SORTING DIRECTORY\\FAKENAME.txt"));
        assertTrue(test.deleteFile("D:\\SAMPLE SORTING DIRECTORY\\test2.txt"));



    }




}