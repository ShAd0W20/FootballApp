package com.shad0wstv.footballapp.utils;

import javafx.scene.control.Alert;

public class NotifyAlert {

    /**
     * @param alertType the type of the alert (ERROR, INFORMATION, WARNING, CONFIRMATION)
     * @param title the title of the alert
     * @param header the header of the alert
     * @param content the content of the alert
     * */
    public static void show(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
