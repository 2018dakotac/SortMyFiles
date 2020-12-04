package SortMyFiles;

import java.io.File;
import java.io.IOException;
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
            System.err.println("Failed to make directory" + e.getMessage());
        }
    }

    public void createDirectory(String directoryPath, String directoryName) {

        createDirectory(combine(directoryPath,directoryName));
    }





    //will manually move a directory and its contents and update the database
    public boolean moveDirectory(String currentPath, String newPath) {
        File from = new File(currentPath);
        //File to = new File(newPath); this moves directory contents to new location
        File to = new File(combine(newPath,from.getName()));

        return recursiveMoveDir(from.toPath(), to.toPath());
    }
    //recursive moving function to allow moving of recursive directories
    private boolean recursiveMoveDir(Path source, Path destination){
        if (source.toFile().isDirectory()) {
            //iterates through the list of files returned  by Files.listFiles
            for (File file : source.toFile().listFiles()) {// NULLPOINTER EXCEPTION CHECK ???????????????????????????????????????????????
                recursiveMoveDir(file.toPath(), destination.resolve(source.relativize(file.toPath())));
            }
        }else{//updates directory for non folder files if they exist within the database
            db.updateFile(source.toString(), destination.normalize().toString());
        }

        try {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            //standard copy operation makes this not fail if used on own directory

            return true;
        } catch (IOException e) {
            return false;
        }

    }
    public void deleteDirectory(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }else{
            //deletes from database
            db.deleteFile(file.getAbsolutePath());
            //System.out.println(file.getAbsolutePath());
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }


    public void copyDirectory(String srcDir, String destDir)
            throws IOException {
        Files.walk(Paths.get(srcDir))
                .forEach(source -> {
                    Path destination = Paths.get(destDir, source.toString()
                            .substring(srcDir.length()));
                    try {
                        Files.copy(source, destination);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    //moves a directory but doesnt update the database
    //needs some error checking like if the directory already exists
    public boolean moveDirectoryNoUpdate(String currentPath, String newPath) {

        File from = new File(currentPath);

        File to = new File(combine(newPath,from.getName()));
        //this will result in moving an exact copy of the directory including the folder itself
        //as a sub folder to the location folder

        try {
            Files.move(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch(DirectoryNotEmptyException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
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
