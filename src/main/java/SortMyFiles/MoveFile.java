package SortMyFiles;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;


public class MoveFile {
    private FileDatabase db;

    public MoveFile(){
        db = new FileDatabase();
    }
    //probably make this a static class or make all functions static
    private String combine(String originalPath, String pathToAdd){
        File original = new File(originalPath);
        return new File(original,pathToAdd).toPath().normalize().toString();
    }


    /*
    this function will change input path to output path (rename it)
     */
    public boolean renameFile(String currentPath,String newName){
        Path path = Path.of(currentPath);
        Path newpath = path.resolveSibling(newName);
        try {
            Files.move(path, newpath, StandardCopyOption.REPLACE_EXISTING);
            //standard copy operation makes this not fail if used on own directory
            db.updateFile(path.toAbsolutePath().normalize().toString(), newpath.toAbsolutePath().normalize().toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
    moves function to path ***file name needs to be included in the destination path...
     */
    public boolean moveFile(String currentPath,String newPath){
        Path from = Path.of(currentPath);
        Path to =  Path.of(newPath);

        try {
            Files.move(from, to);
            //will fail moving to itself
            db.updateFile(from.toAbsolutePath().normalize().toString(), to.toAbsolutePath().normalize().toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




    //this function will copy a single file from one directory to another
    public boolean copyFile(String currentPath,String newPath){
        Path from = Path.of(currentPath);
        Path to =  Path.of(newPath);

        try {
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(from, to, options);
            //standard copy operation makes this not fail if used on own directory
            db.copyFile(from.toRealPath().toString(),to.toRealPath().toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFile(String currentPath) throws IOException{
        File file = new File(currentPath);
        db.deleteFile(file.getCanonicalPath());

        return file.delete();
    }






}
