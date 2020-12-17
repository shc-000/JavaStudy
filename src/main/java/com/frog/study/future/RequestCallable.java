package com.frog.study.future;

import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

public class RequestCallable implements Callable<String> {

    private String requestUrl;

    public RequestCallable(String requestUrl) {

        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public String call() throws Exception {

        if (StringUtils.isEmpty(requestUrl)) {
            return null;
        }

        long beginTime = System.currentTimeMillis();

        // 开始http请求
        //String result = HttpUtils.get(requestUrl);
        //模拟一个超时操作
        Thread.sleep(5000);
        String result = null;
        System.out.println("callable url: " + requestUrl + " 用时 --------"
                + (System.currentTimeMillis() - beginTime) + " ms");

        return result;
    }
}

