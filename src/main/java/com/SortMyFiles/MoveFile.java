package com.SortMyFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;


public class MoveFile {
    private FileDatabase db;

    public MoveFile(){
        db = new FileDatabase();
    }


    /*
    this function will change input path to output path (rename it)
     */
    public boolean renameFile(String currentPath,String newName)throws IOException{
        Path path = Path.of(currentPath);
        Path newpath = path.resolveSibling(newName);
        Files.move(path, newpath, StandardCopyOption.REPLACE_EXISTING);
        //standard copy operation makes this not fail if used on own directory
        db.updateFile(path.toAbsolutePath().normalize().toString(), newpath.toAbsolutePath().normalize().toString());
        return true;

    }
    /*
    moves function to path ***file name needs to be included in the destination path...
     */
    public void moveFile(String currentPath,String newPath) throws IOException{
        Path from = Path.of(currentPath);
        Path to =  Path.of(newPath);
        Files.move(from, to);
        //will fail moving to itself
        db.updateFile(from.toAbsolutePath().normalize().toString(), to.toAbsolutePath().normalize().toString());
    }






    public boolean deleteFile(String currentPath) throws IOException{
        File file = new File(currentPath);
        db.deleteFile(file.getCanonicalPath());

        return file.delete();
    }

    //*****************************LEGACY CODE*****************************************
    //code that we either didnt need or for features we couldn't implement

    //this function will copy a single file from one directory to another
    public void copyFile(String currentPath,String newPath)throws IOException{
        Path from = Path.of(currentPath);
        Path to =  Path.of(newPath);
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(from, to, options);
        //standard copy operation makes this not fail if used on own directory
        db.copyFile(from.toRealPath().toString(),to.toRealPath().toString());
    }




}
