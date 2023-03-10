package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.view.StartActivityView;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthenticatePresenter {
    public LoginPresenter(StartActivityView view) {
        super(view);
    }

    public void validateLogin(String alias, String password) {
       validateAliasAndPassword(alias, password);
    }

    public void login(String alias, String password) {
        userService.login(alias, password, new LoginObserver());
    }

    public class LoginObserver extends AuthenticateObserver {
        @Override
        String createMessage() {
            return "login";
        }
    }
}
