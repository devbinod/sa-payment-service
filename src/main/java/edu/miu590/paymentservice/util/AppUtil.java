package edu.miu590.paymentservice.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtil {
    public static String getCurrentUser() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
