package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHelper {

    static final String RESOURCE_BUNDLE_BASENAME = "strings";

    private static ResourceBundle bundle;

    private LocalizationHelper() {
    }

    private static ResourceBundle getBundle() {
        if (bundle == null) {
            Locale defaultLocale = Locale.getDefault();
            bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASENAME, defaultLocale);
        }
        return bundle;
    }

    public static String getLocalString(String key) {
        return getBundle().getString(key);
    }
}
