package edu.byu.cs.tweeter.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormattedDate {
    public FormattedDate() {}

    public static String getFormattedDate(Long timestamp) {
        return new SimpleDateFormat("E MMM d k:mm:ss z y", Locale.US).format(new Date(timestamp));
    }
}
