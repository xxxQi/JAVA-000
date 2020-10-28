package client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * @author: Administrator
 * @description:
 */
public class HttpClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String request = "http://localhost:8801";

    private void excuteGetReq(String request) {
        try {
            Request build = new Request.Builder().url(request).build();
            Response response = client.newCall(build).execute();
            ResponseBody body = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.clone();
        }
    }

    public static void main(String[] args) {
        new HttpClient().excuteGetReq(request);
    }

}
