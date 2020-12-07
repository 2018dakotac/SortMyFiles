package SortMyFiles.Controllers;

public class table_File {
    private String name, directory;

    public table_File(String name, String directory){
        this.name = name;
        this.directory = directory;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDirectory(){
        return directory;
    }
    public void setDirectory(String directory){
        this.directory = directory;
    }

}
