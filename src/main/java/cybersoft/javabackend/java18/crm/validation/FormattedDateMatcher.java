package cybersoft.javabackend.java18.crm.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormattedDateMatcher {
    private static final String DATE_PATTERN = "^(0?[1-9]|[12][0-9]|3[01])[-|/](0?[1-9]|1[012])[-|/]((?:19|20)[0-9][0-9])$";
    private static final Pattern PATTERN = Pattern.compile(DATE_PATTERN);
    private static final SimpleDateFormat inSDF = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }

    public boolean isValid(final String date) {
        boolean result = false;
        Matcher matcher = PATTERN.matcher(date);
        if (matcher.matches()) {
            result = true;
            String day = matcher.group(1);
            String month = matcher.group(2);
            int year = Integer.parseInt(matcher.group(3));
            if ((month.equals("4") || month.equals("6") || month.equals("9") ||
                    month.equals("04") || month.equals("06") || month.equals("09") ||
                    month.equals("11")) && day.equals("31")) {
                result = false;
            } else if (month.equals("2") || month.equals("02")) {
                if (day.equals("30") || day.equals("31")) {
                    result = false;
                } else if (day.equals("29")) {
                    if (!isLeapYear(year)) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    public String formatDateSQL(String dateString) {
        String dateFormat = "";
        try {
            Date date = inSDF.parse(dateString);
            dateFormat = outSDF.format(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return dateFormat;
    }

    public String formatCommonDate(String dateString) {
        String dateFormat = "";
        try {
            Date date = outSDF.parse(dateString);
            dateFormat = inSDF.format(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return dateFormat;
    }
}
