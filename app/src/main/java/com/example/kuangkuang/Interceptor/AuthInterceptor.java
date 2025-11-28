package com.example.kuangkuang.Interceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token=null;
    private final Set<String> excludePaths = new HashSet<String>() {{
        add("/user/login");
        add("/user/sign");
    }};

    public AuthInterceptor(String token) {
        this.token = token;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String path = originalRequest.url().encodedPath();

        // 检查是否需要跳过认证
        if (shouldSkipAuth(path)) {
            return chain.proceed(originalRequest);
        }

        // 检查token是否有效（不为空且不为空字符串）
        if (token == null || token.trim().isEmpty()) {
            // 如果没有token，仍然继续请求（让服务器返回401）
            return chain.proceed(originalRequest);
        }

        // 添加认证头 - 确保value不为null
        Request authorisedRequest = originalRequest.newBuilder()
                .header("Authorization",token.trim()) // 使用trim()确保没有空格问题
                .build();

        return chain.proceed(authorisedRequest);
    }
    private boolean shouldSkipAuth(String path) {
        for (String excludePath : excludePaths) {
            if (path.startsWith(excludePath)) {
                return true;
            }
        }
        return false;
    }
}
