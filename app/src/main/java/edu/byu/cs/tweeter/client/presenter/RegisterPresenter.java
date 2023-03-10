package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.view.StartActivityView;

public class RegisterPresenter extends AuthenticatePresenter {
    public RegisterPresenter(StartActivityView view) {
        super(view);
    }

    public void register(String firstName, String lastName, String alias, String password,
                         String imageToUpload) {
        userService.register(firstName, lastName, alias, password, imageToUpload, new RegisterObserver());
    }

    public void validateRegistration(String firstName, String lastName, String alias, 
                                     String password, String imageToUpload) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }

        validateAliasAndPassword(alias, password);

        if (imageToUpload == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }

    public class RegisterObserver extends AuthenticateObserver {
        @Override
        String createMessage() {
            return "register";
        }
    }
}
