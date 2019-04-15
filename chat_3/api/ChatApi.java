package com.example.ahmad.chat_3.api;

import com.example.ahmad.chat_3.models.requests.TranslationRequest;
import com.example.ahmad.chat_3.models.requests.chats.SendMessageRequest;
import com.example.ahmad.chat_3.models.responses.BaseResponse;
import com.example.ahmad.chat_3.models.responses.TranslationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatApi {

    @POST("Chat/sendMessage")
    Call<BaseResponse> sendMessage(@Body SendMessageRequest messageRequest);

    @POST("Trasnlation/sendMessage")
    Call<TranslationResponse> translate(@Body TranslationRequest translationRequest);

}
