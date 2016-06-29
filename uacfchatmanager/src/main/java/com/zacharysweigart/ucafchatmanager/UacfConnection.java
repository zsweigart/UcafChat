package com.zacharysweigart.ucafchatmanager;


import android.support.annotation.VisibleForTesting;

import com.zacharysweigart.ucafchatmanager.model.Message;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class UacfConnection {
    @VisibleForTesting
    static boolean LOG = false;

    @VisibleForTesting
    HttpLoggingInterceptor interceptor;
    @VisibleForTesting
    UacfApi uacfApi;
    private Retrofit retrofit;

    public UacfConnection() {

        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(LOG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(UacfApi.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        uacfApi = retrofit.create(UacfApi.class);
    }

    public Call<List<Message>> getMessages(String userName) {
        return uacfApi.getMessages(userName);
    }

    public Call<Message> postMessage(Message message) {
        return uacfApi.sendMessage(message);
    }
}
