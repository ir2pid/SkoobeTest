package com.noisyninja.skoobetest;

/**
 * Created by sudiptadutta on 23/09/16.
 */
public class Constants {

    public static String PROGRESS_TEXT = "Working...";
    public static String PROGRESS_PERCENT = "%";
    public static String DOWNLOAD_TEXT = "Downloading file..";
    public static long ANIMATION_TIME_700 = 700;    // Sleep for some time

    public static String URL_STORE = "https://skoobe-website.s3.amazonaws.com/skoobe-test/skoobe_api.json";

    public enum PROGRESS_STYLE {
        DETERMINATE,
        INDETERMINATE
    }
}
