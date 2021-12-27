package com.ebuild.commerce.util;

public class RegexUtils {

    /**
     * 원본 문자열에서 특정 HTML 태그를 변환한다.(도서정보중 해제, 목차 등등에 원하지 않는 html tag 를 변환한다.)
     * @param org
     * @return
     */
    public static String convertContentHTML(String org) {
        String sRetValue = "";
        if (org == null) return "";
        sRetValue	= org.replaceAll("<hr size=0.5>","<br>");
        sRetValue	= sRetValue.replaceAll("&nbsp;" , " ");
        sRetValue	= sRetValue.replaceAll("<hr>" , " ");
        sRetValue	= sRetValue.replaceAll("</p>" , " ");
        sRetValue	= sRetValue.replaceAll("<p>" , "<br><br>");
        sRetValue	= sRetValue.replaceAll("\n" , "<br>");
        return sRetValue;
    }

    public static String removeSpecialCharacter(String source) {
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        return source.replaceAll(match, " ");
    }

    public static String ellipsis(String source, int limit){
        if (source.length() < limit)
            return source;
        return source.substring(0,limit-3) + "...";
    }

    public static String camelCaseToUnderScoreUpperCase(String camelCase) {
        String result = "";
        boolean prevUpperCase = false;
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (!Character.isLetter(c))
                return camelCase;
            if (Character.isUpperCase(c)) {
                if (prevUpperCase)
                    return camelCase;
                result += "_" + c;
                prevUpperCase = true;
            } else {
                result += Character.toUpperCase(c);
                prevUpperCase = false;
            }
        }
        return result.substring(1);
    }
}
