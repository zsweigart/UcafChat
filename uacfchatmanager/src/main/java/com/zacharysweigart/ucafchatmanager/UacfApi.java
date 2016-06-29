package com.zacharysweigart.ucafchatmanager;


import com.zacharysweigart.ucafchatmanager.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface UacfApi {
    String BASE_URL = "https://damp-lake-90950.herokuapp.com/";
    String CHAT_URL = "chat";
    String CHAT_REFRESH_URL = "chat/{username}";

    @POST(CHAT_URL)
    Call<Message> sendMessage(@Body Message message);


    @GET(CHAT_REFRESH_URL)
    Call<List<Message>> getMessages(@Path("username") String userName);
}
