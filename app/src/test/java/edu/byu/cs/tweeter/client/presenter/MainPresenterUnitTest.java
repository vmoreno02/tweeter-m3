package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.view.MainView;
import edu.byu.cs.tweeter.model.domain.Status;

public class MainPresenterUnitTest {
    private MainView mockView;
    private StatusService mockStatusService;

    private MainPresenter mainPresenterSpy;
    private Status status;

    @BeforeEach
    public void setup() {
        status = new Status();
        mockView = Mockito.mock(MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));
        Mockito.when(mainPresenterSpy.getStatusService()).thenReturn(mockStatusService);
    }

    @Test
    public void testPostStatus_PostSuccessful() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostStatusObserver observer = invocation.getArgument(1, MainPresenter.PostStatusObserver.class);

                Status testStatus = invocation.getArgument(0, Status.class);
                Assertions.assertEquals(testStatus, status);

                observer.handleSuccess();
                return null;
            }
        };

        doPostStatus(answer);

        Mockito.verify(mockView).displayMessage("Successfully Posted!");
        Mockito.verify(mockView).cancelPostingToast();
    }

    @Test
    public void testPostStatus_PostError() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostStatusObserver observer = invocation.getArgument(1, MainPresenter.PostStatusObserver.class);
                observer.handleFailure("an error message");
                return null;
            }
        };

        doPostStatus(answer);

        verifyNotSuccess("Failed to post status: an error message");
    }

    @Test
    public void testPostStatus_PostException() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostStatusObserver observer = invocation.getArgument(1, MainPresenter.PostStatusObserver.class);
                observer.handleException(new Exception("an exception"));
                return null;
            }
        };

        doPostStatus(answer);

        verifyNotSuccess("Failed to post status because of exception: an exception");
    }

    private void doPostStatus(Answer answer) {
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus(status);
    }

    private void verifyNotSuccess(String message) {
        Mockito.verify(mockView).displayMessage(message);
        Mockito.verify(mockView, Mockito.times(0)).cancelPostingToast();
    }
}

