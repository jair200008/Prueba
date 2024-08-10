package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValid(String email) {
        if (email == null){
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
