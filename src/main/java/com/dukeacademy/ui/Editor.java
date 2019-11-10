package com.dukeacademy.ui;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.stream.Stream;

import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;

import com.dukeacademy.observable.Observable;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Creates a Text Editor window for the user to input code.
 */
public class Editor extends UiPart<Region> {
    private static final String FXML = "Editor.fxml";
    private SimpleIntegerProperty numberOfLines = new SimpleIntegerProperty(1);

    @FXML
    private TextArea lineCounter;

    @FXML
    private TextArea textOutput;

    /**
     * Instantiates a new Editor.
     *
     * @param questionObservable the question observable
     */
    public Editor(Observable<Question> questionObservable) {
        super(FXML);

        initialize();
        textOutput.setPadding(new Insets(15, 20, 15, 20));

        questionObservable.addListener(question -> {
            if (question != null) {
                UserProgram savedProgram = question.getUserProgram();
                this.textOutput.setText(savedProgram.getSourceCode());
            }
        });
    }

    /**
     * Initializes the initial configurations for the Editor Window upon startup.
     */
    @FXML
    public void initialize() {
        textOutput.addEventHandler(KeyEvent.KEY_PRESSED, e1 -> {
            if (e1.getCode() == KeyCode.TAB) {
                textOutput.insertText(textOutput.getCaretPosition(), " ".repeat(2));
                e1.consume();
            } else if (e1.isShiftDown() && e1.getCode() == KeyCode.CLOSE_BRACKET) {
                int currentCaretPos = textOutput.getCaretPosition();
                boolean isEmptyLineAtCaretPosition = true; // need further changes

                if (isEmptyLineAtCaretPosition) {
                    textOutput.deleteText(currentCaretPos - 2, currentCaretPos - 1);
                }
            }
        });

        textOutput.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                long leftBraceCount = textOutput.textProperty().getValue().chars()
                        .filter(ch -> ch == '{')
                        .count();

                long rightBraceCount = textOutput.textProperty().getValue().chars()
                        .filter(ch -> ch == '}')
                        .count();

                long indentationCount = leftBraceCount - rightBraceCount;

                if (indentationCount > 0) {
                    String tab = " ".repeat(2);
                    textOutput.insertText(textOutput.getCaretPosition(), tab.repeat((int) indentationCount));
                }
            }
        });

        textOutput.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                numberOfLines.setValue((textOutput.getText() + " ").split("\n").length);
                lineCounter.setText(generateLineCounterInput(numberOfLines));
            }
        });

        Platform.runLater(() -> {
            ScrollBar editorsb = (ScrollBar) textOutput.lookup(".scroll-bar:vertical");
            ScrollBar lineCountersb = (ScrollBar) lineCounter.lookup(".scroll-bar:vertical");

            lineCountersb.valueProperty().bind(editorsb.valueProperty());
        });
    }

    /**
     * Saves file into user's computer upon clicking the "Save" button.
     *
     * @param e the ActionEvent
     * @throws IOException when the user's file cannot be accessed
     */
    @FXML
    public void onSaveButtonClick(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save File");
        File selectedFile = chooser.showOpenDialog(stage);
        FileWriter fw = new FileWriter(selectedFile.getAbsolutePath());
        fw.write(textOutput.getText());
        fw.close();
    }

    /**
     * Returns the current text in the editor.
     *
     * @return current text in editor.
     */
    public UserProgram getUserProgram() {
        return new UserProgram("Main", textOutput.getText().strip());
    }

    private String generateLineCounterInput(SimpleIntegerProperty n) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= n.getValue(); i++) {
            if (i == 1) {
                sb.append(i);
            } else {
                sb.append("\n" + i);
            }
        }

        return sb.toString();
    }
}
