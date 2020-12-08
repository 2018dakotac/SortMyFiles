package SortMyFiles;

import java.io.IOException;

public class SortFile {

    //private FileDatabase db;
    private DirectoryFunctions dirFunc;
    private MoveFile mvFile;

    public SortFile(){
        //db = new FileDatabase();
        dirFunc = new DirectoryFunctions();
        mvFile = new MoveFile();
    }

    public void tagSort(String currentPath,String newPath, String tag) throws IOException{


    }
    public void extensionSortRecursive(String currentPath,String newPath, String tag) throws IOException{


    }




}
