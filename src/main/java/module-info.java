module SortMyFiles {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens SortMyFiles to javafx.fxml;
    opens SortMyFiles.Controllers;

    exports SortMyFiles;

}