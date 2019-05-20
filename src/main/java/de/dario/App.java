package de.dario;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;

public class App extends Application {

    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab tabG;
    @FXML
    private Tab tabP;

    @FXML
    private JFXTextField newPassField;
    @FXML
    private JFXTextField hashField;
    @FXML
    private JFXTextField passField;
    @FXML
    private JFXTextField hash2Field;
    @FXML
    private JFXTextField resultField;
    @FXML
    private JFXButton generate;
    @FXML
    private JFXButton copy;
    @FXML
    private JFXButton paste;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXButton check;
    @FXML
    private JFXButton clear21;

    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final ClipboardContent content;
    private final Logger logger = Logger.getLogger(App.class.getName());

    public App() {
        tabPane = new JFXTabPane();
        content = new ClipboardContent();
    }

    @FXML
    private void initialize() {
        newPassField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    generate.setDisable(newValue.equals(""));
            }
        });
    }

    @Override
    public void start(Stage startStage) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("../../mainView.fxml"));
            BorderPane root = loader.load();
            Stage mStage = new Stage();
            mStage.setTitle("BCrypt Hashy");
            Scene scene = new Scene(root);
            mStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("../../application.css").toExternalForm());
            mStage.setResizable(false);
            mStage.show();
            root.requestFocus();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "context", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void generateA(ActionEvent g) {
        hashField.setText(BCrypt.hashpw(newPassField.getText(), BCrypt.gensalt()));
    }

    public void checkA(ActionEvent g) {
        try {
            if (BCrypt.checkpw(passField.getText(), hash2Field.getText())) {
                resultField.setText("Password and hash value match.");
            } else {
                resultField.setText("Password and hash value don't match");
            }
        } catch (IllegalArgumentException e) {
            resultField.setText("No valid hash value!");
        } catch (StringIndexOutOfBoundsException e) {
            resultField.setText("No hash value available!");

        }
    }

    public void clearA(ActionEvent g) {
        newPassField.clear();
        hashField.clear();
    }

    public void clear2A(ActionEvent g) {
        passField.clear();
        hash2Field.clear();
        resultField.clear();
    }

    public void copyA(ActionEvent g) {
        content.putString(hashField.getText());
        clipboard.setContent(content);
    }

    public void pasteA(ActionEvent g) {
        hash2Field.setText(clipboard.getString());
    }

}

