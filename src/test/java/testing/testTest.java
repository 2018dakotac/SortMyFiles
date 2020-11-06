package testing;

import org.junit.*;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.DEFAULT)
public class testTest {
    @BeforeClass
    public static void createStuff(){
        //database?
    }
    @Before
    public void init(){

    }
    @After
    public void cleanUP() {

    }

    @Test
    public void unitTest(){
        System.out.println("test");
    }


}