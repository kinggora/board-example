package message;

public enum ErrorMessage {

    NOT_EXISTS_BOARD("게시물이 존재하지 않습니다."),
    INVALID_BNO("게시글 번호는 양의 정수로 입력해주세요."),
    INVALID_TITLE("제목은 1~20자로 입력해주세요."),
    INVALID_CONTENT("내용은 1~50자로 입력해주세요."),
    INVALID_WRITER("작성자는 1~20자로 입력해주세요.");

    private String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
