package GPT.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd"; // 날짜 형식

    // 날짜를 문자열로 변환
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    // 문자열을 날짜로 변환
    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.parse(dateString);
    }

    // 두 날짜를 비교
    public static boolean isBefore(Date date1, Date date2) {
        return date1.before(date2);
    }

    public static boolean isAfter(Date date1, Date date2) {
        return date1.after(date2);
    }
}
