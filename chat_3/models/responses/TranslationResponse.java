package com.example.ahmad.chat_3.models.responses;

import com.google.gson.annotations.SerializedName;

public class TranslationResponse extends BaseResponse {

    @SerializedName("translation")
    public String translation;
}
