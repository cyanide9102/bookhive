package com.cyanide9102.orderservice.context;

import java.util.List;

public class UserContext {

    private static final ThreadLocal<String> userId = new ThreadLocal<>();
    private static final ThreadLocal<List<String>> userRoles = new ThreadLocal<>();

    public static String getUserId() {

        return userId.get();
    }

    public static void setUserId(String id) {

        userId.set(id);
    }

    public static List<String> getUserRoles() {

        return userRoles.get();
    }

    public static void setUserRoles(List<String> roles) {

        userRoles.set(roles);
    }

    public static void clear() {

        userId.remove();
        userRoles.remove();
    }
}
