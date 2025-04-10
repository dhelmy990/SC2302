package services;

public interface IUserInputHandler {
    String getNonEmptyInput(String prompt);

    String getInput(String prompt);

    boolean getYesNoInput(String prompt);

    int getValidIntegerInput(String prompt, int min, int max);
}