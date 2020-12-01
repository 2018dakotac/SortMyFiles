package SortMyFiles;

import java.nio.file.Paths;

public class Directory {

    //untested should return current working directory
    public String currentWorkingDirectory() {
        return Paths.get("").toAbsolutePath().toString();
    }
}
