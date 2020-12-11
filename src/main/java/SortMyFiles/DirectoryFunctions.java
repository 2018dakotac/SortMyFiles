package SortMyFiles;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;


public class DirectoryFunctions {
    private FileDatabase db;

    public DirectoryFunctions(){
        db = new FileDatabase();
    }


    //untested should return current working directory
    public String currentWorkingDirectory() {
        return Paths.get("").toAbsolutePath().toString();
    }

    /*
   this function will create all unmade directories in the given path
    */
    public void createDirectory(String pathToMake) throws IOException{
        //create new directory does nothing if it already exists

            Path target = Paths.get(pathToMake);
            Files.createDirectories(target);
    }

    public void createDirectory(String directoryPath, String directoryName) throws IOException{

        createDirectory(miscFunc.combine(directoryPath,directoryName));
    }





    //will move the entire directory including the folder itself
    public void moveDirectory(String currentPath, String newPath) throws IOException {
        File from = new File(currentPath);
        //File to = new File(newPath); this moves directory contents to new location
        File to = new File(miscFunc.combine(newPath,from.getName()));

        recursiveMoveDir(from.toPath(), to.toPath());
    }
    //will  move a directory's content
    public void moveDirectoryContents(String currentPath, String newPath) throws IOException {
        File from = new File(currentPath);
        //File to = new File(newPath); this moves directory contents to new location
        File to = new File(newPath);

        recursiveMoveDir(from.toPath(), to.toPath());
    }
    //https://stackoverflow.com/questions/34820734/java-moving-files-within-filesystem
    private void recursiveMoveDir(Path source, Path destination) throws IOException{
        Files.createDirectories(destination);
        Files.walk(source)
                .filter(Files::isDirectory)
                .forEach(path -> {
                    try{
                        Files.createDirectories(path);
                    }catch(IOException e){
                        //e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
        Files.walk(source)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    Path dest = destination.resolve(source.relativize(path));
                    try{
                        Files.createDirectories(dest.getParent());
                        //Files.move(path,dest,StandardCopyOption.REPLACE_EXISTING);
                        Files.move(path,dest);
                        db.updateFile(path.toAbsolutePath().normalize().toString(), dest.toAbsolutePath().normalize().toString());
                    }catch(IOException e){
                        //e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
        //delete left over file structure...
        recursiveDelDir(source.toFile());
    }


    public void deleteDirectory(String path) throws IOException{
        recursiveDelDir(new File(path));
    }
    private void recursiveDelDir(File file) throws IOException {
        if(file.exists()) {
            if (file.isDirectory()) {
                File[] entries = file.listFiles();
                if (entries != null) {
                    for (File entry : entries) {
                        recursiveDelDir(entry);
                    }
                }
            } else {
                //deletes from database
                db.deleteFile(file.getCanonicalPath());
                //System.out.println(file.getAbsolutePath());
            }
            if (!file.delete()) {
                throw new IOException("Failed to delete " + file);
            }
        }
    }

    //will copy a directory itself and its contents and update the database
    public void copyDirectory(String currentPath, String newPath) throws IOException {
        File from = new File(currentPath);
        //File to = new File(newPath); this moves directory contents to new location
        File to = new File(miscFunc.combine(newPath,from.getName()));

         recursiveCopyDir(from.toPath(), to.toPath());
    }

    //will only copy the contents of specified directory
    public void copyDirectoryContents(String currentPath, String newPath) throws IOException {
        File from = new File(currentPath);
        //File to = new File(newPath); this moves directory contents to new location
        File to = new File(newPath);

         recursiveCopyDir(from.toPath(), to.toPath());
    }

    private void recursiveCopyDir(Path source, Path destination) throws IOException{
        Files.createDirectories(destination);
        Files.walk(source)
                .filter(Files::isDirectory)
                .forEach(path -> {
                    try{
                        Files.createDirectories(path);
                    }catch(IOException e){
                        //e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
        Files.walk(source)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    Path dest = destination.resolve(source.relativize(path));
                    try{
                        Files.createDirectories(dest.getParent());
                        //Files.copy(path,dest,StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(path,dest);
                        db.copyFile(path.toAbsolutePath().normalize().toString(), dest.toAbsolutePath().normalize().toString());
                    }catch(IOException e){
                        //e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
    }
    //not enough time
    public boolean renameDirectory(String currentPath,String newPath){
       return false;
    }





}
