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
    /*
  https://softwareengineering.stackexchange.com/questions/267846/is-there-a-conventional-way-to-combine-file-path-strings
  combines paths leveraging the file api, used for keeping the directory in original folder instead of just moving contents
    */
    private String combine(String originalPath, String pathToAdd){
        File original = new File(originalPath);
        return new File(original,pathToAdd).toPath().normalize().toString();
    }

    //untested should return current working directory
    public String currentWorkingDirectory() {
        return Paths.get("").toAbsolutePath().toString();
    }

    /*
   this function will create all unmade directories in the given path
    */
    public void createDirectory(String pathToMake) {
        //create new directory does nothing if it already exists
        try {
            Path target = Paths.get(pathToMake);
            Files.createDirectories(target);
        } catch (IOException e) {
            e.printStackTrace();
            //System.err.println("Failed to make directory" + e.getMessage());
        }
    }

    public void createDirectory(String directoryPath, String directoryName) {

        createDirectory(combine(directoryPath,directoryName));
    }





    //will move the entire directory including the folder itself
    public void moveDirectory(String currentPath, String newPath) throws IOException {
        File from = new File(currentPath);
        //File to = new File(newPath); this moves directory contents to new location
        File to = new File(combine(newPath,from.getName()));

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
                        e.printStackTrace();
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
                        e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
    }


    /*
    //recursive moving function to allow moving of recursive directories
    private void recursiveMoveDir(Path source, Path destination) throws IOException {
        if (source.toFile().isDirectory()) {
            //iterates through the list of files returned  by Files.listFiles
            for (File file : source.toFile().listFiles()) {// NULLPOINTER EXCEPTION CHECK ?????????????????
                recursiveMoveDir(file.toPath(), destination.resolve(source.relativize(file.toPath())));
            }
        }else{//updates directory for non folder files if they exist within the database

            //db.updateFile(source.toRealPath().toString(),destination.toRealPath().toString());
            //while real path is better it requires both of them to exist on the disk at the same time which isnt possible.
            db.updateFile(source.toAbsolutePath().normalize().toString(), destination.toAbsolutePath().normalize().toString());
        }

            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            //standard copy operation makes this not fail if used on own directory

    }

     */

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
        File to = new File(combine(newPath,from.getName()));

         recursiveCopyDir(from.toPath(), to.toPath());
    }

    //will only copy the contents of specified directory
    public void copyDirectoryContents(String currentPath, String newPath) throws IOException {
        File from = new File(currentPath);
        //File to = new File(newPath); this moves directory contents to new location
        File to = new File(newPath);

         recursiveCopyDir(from.toPath(), to.toPath());
    }
    /*
    //recursive moving function to allow moving of recursive directories
    private void recursiveCopyDir(Path source, Path destination) throws IOException {
        if (source.toFile().isDirectory()) {
            //iterates through the list of files returned  by Files.listFiles
            for (File file : source.toFile().listFiles()) {// NULLPOINTER EXCEPTION CHECK ?????????????????
                recursiveMoveDir(file.toPath(), destination.resolve(source.relativize(file.toPath())));
            }
        }
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            db.copyFile(source.toRealPath().toString(),destination.toRealPath().toString());
            //standard copy operation makes this not fail if used on own directory
    }

     */
    private void recursiveCopyDir(Path source, Path destination) throws IOException{
        Files.createDirectories(destination);
        Files.walk(source)
                .filter(Files::isDirectory)
                .forEach(path -> {
                    try{
                        Files.createDirectories(path);
                    }catch(IOException e){
                        e.printStackTrace();
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
                        e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
    }

    public boolean renameDirectory(String currentPath,String newPath){
       return false;
    }



    /*
    //untested just incase deleteDirectory1 isnt suitable
    public void deleteDirectoryBackup(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectory2(entry);
                    //deletes from database

                }
            }
        }
        Files.delete(path);
        db.deleteFile(path.toAbsolutePath().toString());
    }
     */

}
