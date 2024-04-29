package com.example.petstorebackend.AccountLogAndRegister.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static String extractSrc(String input) {
        String srcRegex = "src=\"([^\"]+)\"";
        Pattern srcPattern = Pattern.compile(srcRegex);
        Matcher srcMatcher = srcPattern.matcher(input);
        if (srcMatcher.find()) {
            return srcMatcher.group(1);
        }
        return null;
    }

    public static String extractTextDescription(String input) {
        String textRegex = ">([^>]+)";
        Pattern textPattern = Pattern.compile(textRegex);
        Matcher textMatcher = textPattern.matcher(input);
        if (textMatcher.find()) {
            return textMatcher.group(1);
        }
        return null;
    }
}
