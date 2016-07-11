package texus.autozoneuae.network;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import texus.autozoneuae.utility.Utility;

/**
 * Created by sandeep on 1/7/16.
 */
public class NetworkService {

    public static String get(String url) {
        try {
            OkHttpClient client =  new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch ( Exception e) {
            e.printStackTrace();;
        }
        return "";

    }

    public static void getAndSave(String url, String fileName) {
        Log.e("NetworkService","URL:" + url);
        Log.e("NetworkService","File Name:" + fileName);
        try {
            OkHttpClient client =  new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String responseString  = response.body().string();
            Log.e("NetworkService","responseString:" + responseString);
            Utility.write(fileName, responseString);

        } catch ( Exception e) {
            e.printStackTrace();;
        }


    }




}