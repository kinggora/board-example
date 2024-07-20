package message;

public enum Label {
    LIST("[게시글 목록]"),
    CREATE("[새 게시물 입력]"),
    UPDATE("[수정 내용 입력]"),
    READ("[게시물 읽기]"),
    CLEAR("[게시물 전체 삭제]"),
    BNO("번호: "),
    TITLE("제목: "),
    CONTENT("내용: "),
    WRITER("작성자: "),
    DATE("날짜: "),
    MAIN_MENU("메인 메뉴: "),
    SUB_MENU("보조 메뉴: "),
    MENU_SELECT("메뉴 선택: "),
    EXIT_MASSAGE("*** 프로그램 종료 ***"),
    HORIZONTAL_LINE("-----------------------------------------------------------"),
    HORIZONTAL_SHARP("############");

    private final String value;

    Label(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
