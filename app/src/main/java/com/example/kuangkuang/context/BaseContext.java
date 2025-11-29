package com.example.kuangkuang.context;

import com.example.kuangkuang.entity.User;

public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
    public static void setTokenThreadLocal(String token){tokenThreadLocal.set(token);}

    public static long getCurrentId() {
        return threadLocal.get();
    }
    public static void setCurrentUser(User user){userThreadLocal.set(user);}
    public static User getCurrentUser(){return userThreadLocal.get();}
    public static void removeCurrentId() {
        threadLocal.remove();
    }
    public static void removeCurrentUser(){userThreadLocal.remove();}

    public static String getToken() {
        return tokenThreadLocal.get();
    }
}
