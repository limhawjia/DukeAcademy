package com.dukeacademy.ui;

import java.util.List;

import com.dukeacademy.model.program.TestCaseResult;

import javafx.fxml.FXML;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;


/**
 * Controller class for run code result, an evaluation of user's code submission against the test cases specified by
 * the question.
 */
public class CodeResultPanel extends UiPart<Region> {
    private static final String FXML = "CodeResultPanel.fxml";

    @FXML
    private TitledPane testCasePane1;
    @FXML
    private TitledPane testCasePane2;
    @FXML
    private TitledPane testCasePane3;

    @FXML
    private Text input1;
    @FXML
    private Text expectedOutput1;
    @FXML
    private Text actualOutput1;

    @FXML
    private Text input2;
    @FXML
    private Text expectedOutput2;
    @FXML
    private Text actualOutput2;

    @FXML
    private Text input3;
    @FXML
    private Text expectedOutput3;
    @FXML
    private Text actualOutput3;

    public CodeResultPanel(List<TestCaseResult> testCaseResults) {
        super(FXML);

        for (int i = 0; i < testCaseResults.size(); i++) {
            fillTestCasePane(testCaseResults.get(i), i + 1);
        }

        for (int j = 0; j < testCaseResults.size(); j++) {
            displayGrade(testCaseResults.get(j), j + 1);
        }
    }

    /**
     * Displays the grade received for the program the user submitted, against a specific test case.
     * @param testCaseResult test case result object
     * @param testCaseNumber id of test case
     */
    private void displayGrade(TestCaseResult testCaseResult, int testCaseNumber) {
        boolean isSuccessful = testCaseResult.isSuccessful();

        if (testCaseNumber == 1) {
            writeAppropriateGrade(isSuccessful, testCasePane1,
                    "Test Case 1 (PASSED)", "Test Case 1 (FAILED)");
        } else if (testCaseNumber == 2) {
            writeAppropriateGrade(isSuccessful, testCasePane2,
                    "Test Case 2 (PASSED)", "Test Case 2 (FAILED)");
        } else {
            writeAppropriateGrade(isSuccessful, testCasePane3,
                    "Test Case 3 (PASSED)", "Test Case 3 (FAILED)");
        }
    }

    /**
     * Helper method for displayGrade().
     * @param isSuccessful a boolean to indicate whether user's program passed the specified test case
     * @param testCasePane the specific pane for the specified test case
     * @param passString a string to indicate that user's program has passed the specified test case
     * @param failString a string to indicate that user's program has passed the specified test case
     */
    private void writeAppropriateGrade(boolean isSuccessful, TitledPane testCasePane,
                                       String passString, String failString) {
        if (isSuccessful) {
            testCasePane.setText(passString);
        } else {
            testCasePane.setText(failString);
        }
    }


    /**
     * Fills a test case pane with essential information about the test case, when ran against the
     * user's program.
     * @param testCaseResult test case result object
     * @param testCaseNumber id of test case
     */
    private void fillTestCasePane(TestCaseResult testCaseResult, int testCaseNumber) {
        if (testCaseNumber == 1) {
            writePaneContents(input1, expectedOutput1, actualOutput1, testCaseResult);
        } else if (testCaseNumber == 2) {
            writePaneContents(input2, expectedOutput2, actualOutput2, testCaseResult);
        } else {
            writePaneContents(input3, expectedOutput3, actualOutput3, testCaseResult);
        }
    }

    /**
     * Helper method for fillTestCasePane()
     * @param input given input for the specified test case
     * @param expectedOutput expected output for the specified test case
     * @param actualOutput actual output generated by user's program
     * @param testCaseResult test case result object
     */
    private void writePaneContents(Text input, Text expectedOutput, Text actualOutput, TestCaseResult testCaseResult) {
        String inputString = testCaseResult.getInput();
        input.setText(inputString);
        String expectedOutputString = testCaseResult.getExpectedOutput();
        expectedOutput.setText(expectedOutputString);
        String actualOutputString = testCaseResult.getActualOutput().get();
        actualOutput.setText(actualOutputString);
    }
}
