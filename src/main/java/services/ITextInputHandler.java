package services;

public interface ITextInputHandler {
    String getNonEmptyInput(String prompt);

    String getInput(String prompt);

    String getNumericStringInput(String prompt);
}
