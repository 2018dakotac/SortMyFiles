package SortMyFiles;

import java.util.ArrayList;

//<https://www.marcobehler.com/guides/java-files>
import java.nio.file.Path;
import java.nio.file.Paths;


public class MoveFile {
    //probably make this a static class or make all functions static

    /*
    this function will copy a file form one place to another
    TODO: if we make any other functions that involve creating files we should make another class file
     */
    public boolean copyFile(String currentPath,String name,String newPath){


        return true;
    }

    //this function will move a single file from one directory to another
    public boolean moveFile(String currentPath,String name,String newPath){

        return true;
    }

    /*this function will move a list of files from one directory to another
    return true if all files were moved successfully
   return false if at least one fails
    */
    public boolean moveFiles(String currentPath, ArrayList<String> names, String newPath){

        return true;
    }


    /*
    this function will move all functions with a certain extension from a target directory to a location directory.
    will have an option to implement recursive moving
     */
    public boolean moveFileExt(String currentPath, String fileExtension, String newPath, boolean recursiveMove){

        return true;
    }



}
