package services;

import users.User;
import java.util.List;


public class AccountUpdateService implements IAccountUpdateService {
    private final ITextInputHandler textInputHandler;
    private final IBooleanInputHandler booleanInputHandler;
    private final IDuplicateCheckService duplicateCheckService;

    public AccountUpdateService(ITextInputHandler textInputHandler, IBooleanInputHandler booleanInputHandler, IDuplicateCheckService duplicateCheckService) {
        this.textInputHandler = textInputHandler;
        this.booleanInputHandler = booleanInputHandler;
        this.duplicateCheckService = duplicateCheckService;
    }

    @Override
    public void updateAccount(User user, List<User> users) {
        System.out.println("\n--- My Account ---");
        user.displayUserInfo();

        if (!booleanInputHandler.getYesNoInput("Do you want to update your info?")) {
            System.out.println("No changes made.");
            return;
        }

        String newUsername = user.getUsername();
        String newEmail = user.getEmail();
        String newPassword = user.getPassword();

        if (booleanInputHandler.getYesNoInput("Update username?")) {
            newUsername = getUniqueInput("Enter new username: ",
                    input -> duplicateCheckService.isUsernameAvailable(input, user, users));
        }

        if (booleanInputHandler.getYesNoInput("Update email?")) {
            newEmail = getUniqueInput("Enter new email: ",
                    input -> duplicateCheckService.isEmailAvailable(input, user, users));
        }

        if (booleanInputHandler.getYesNoInput("Update password?")) {
            newPassword = textInputHandler.getNonEmptyInput("Enter new password: ");
        }

        user.updateUserInfo(newUsername, newEmail, newPassword);
        System.out.println("Your information has been updated.");
    }

    private String getUniqueInput(String prompt, IFieldValidator validator) {
        while (true) {
            String input = textInputHandler.getNonEmptyInput(prompt);
            if (validator.validate(input)) {
                return input;
            }
            System.out.println("Input is not valid. Please try again.");
        }
    }
}