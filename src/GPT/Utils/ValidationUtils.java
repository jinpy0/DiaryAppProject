package GPT.Utils;

import java.text.ParseException;

public class ValidationUtils {
    // 문자열이 비어있는지 확인
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // 유효한 날짜인지 확인
    public static boolean isValidDate(String dateString) {
        try {
            DateUtils.parseDate(dateString);
            return true;
        } catch (ParseException e) {
            return false; // 예외 발생 시 유효하지 않은 날짜
        }
    }

    // 특정 길이의 문자열인지 확인
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        return str.length() >= minLength && str.length() <= maxLength;
    }
}
