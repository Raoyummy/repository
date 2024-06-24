package com.example.wechat.dbutil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//封装一个访问网络的工具类,是发送JSON数据和接收服务器返回的数据
public class InternetUtil {

    private static OkHttpClient client = new OkHttpClient();
    /**
     * 请求 * * @param url * @return * @throws IOException
     */
    public static String post(String url,String jsonstr) throws IOException {
        //设置提交的格式为json
        MediaType json = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(json,jsonstr);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
