package service;

public class BoardValidator {

    private final static int TITLE_MAX_LENGTH = 20;
    private final static int CONTENT_MAX_LENGTH = 50;
    private final static int WRITER_MAX_LENGTH = 20;

    public static boolean validateTitle(String title) {
        if(isEmptyValue(title)) {
            return false;
        }
        return title.length() < TITLE_MAX_LENGTH;
    }

    public static boolean validateContent(String content) {
        if(isEmptyValue(content)) {
            return false;
        }
        return content.length() < CONTENT_MAX_LENGTH;
    }

    public static boolean validateWriter(String writer) {
        if(isEmptyValue(writer)) {
            return false;
        }
        return writer.length() < WRITER_MAX_LENGTH;
    }

    public static boolean validateBno(String bno) {
        if(!isIntegerValue(bno)) {
            return false;
        }
        return Integer.parseInt(bno) > 0;
    }

    private static boolean isEmptyValue(String str) {
        return str == null || str.isBlank();
    }

    private static boolean isIntegerValue(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
