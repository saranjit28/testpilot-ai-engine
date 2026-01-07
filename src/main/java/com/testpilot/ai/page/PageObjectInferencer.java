package com.testpilot.ai.page;

import java.util.Set;

public class PageObjectInferencer {

    public static PageInferenceResult infer(Set<String> keywords) {

        String page = inferPage(keywords);
        String action = inferAction(keywords);

        return new PageInferenceResult(page, action);
    }

    private static String inferPage(Set<String> keywords) {

        if (keywords.contains("login")) return "LoginPage";
        if (keywords.contains("dashboard")) return "DashboardPage";
        if (keywords.contains("checkout")) return "CheckoutPage";
        if (keywords.contains("payment")) return "PaymentPage";
        if (keywords.contains("profile")) return "ProfilePage";
        if (keywords.contains("search")) return "SearchPage";

        return "CommonPage";
    }

    private static String inferAction(Set<String> keywords) {

        if (keywords.contains("click")) return "click";
        if (keywords.contains("tap")) return "click";

        if (keywords.contains("enter")) return "enter";
        if (keywords.contains("type")) return "enter";
        if (keywords.contains("fill")) return "enter";

        if (keywords.contains("select")) return "select";
        if (keywords.contains("choose")) return "select";

        if (keywords.contains("verify")) return "verify";
        if (keywords.contains("see")) return "verify";
        if (keywords.contains("validate")) return "verify";

        return "perform";
    }
}