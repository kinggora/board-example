package exception;

import message.ErrorMessage;

public class BoardException extends RuntimeException {

    public BoardException(ErrorMessage error) {
        super(error.toString());
    }
}
