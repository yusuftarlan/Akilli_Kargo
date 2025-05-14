package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControlMain {

    @FXML
    private Button butKar;

    @FXML
    private Button butMar;

    @FXML
    private Button butSat;

    @FXML
    void sayKar(ActionEvent event) {
        System.out.println("kargo bölümü");
    }

    @FXML
    void sayMar(ActionEvent event) {
        System.out.println("market bölümü");
    }

    @FXML
    void saySat(ActionEvent event){
        System.out.println("satıcı bölümü");
    }

}
