package services;

public interface INumericInputHandler {
    int getValidIntegerInput(String prompt, int min, int max);
    double getValidDoubleInput(String prompt);
    double getValidDoubleInput(String prompt, double min, double max);
  
}
