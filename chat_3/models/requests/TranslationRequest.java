package com.example.ahmad.chat_3.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslationRequest {

    @SerializedName("lang_code_from")
    @Expose
    public String langCodeFrom;

    @SerializedName("lang_code_to")
    @Expose
    public String langCodeTo;

    @SerializedName("text")
    @Expose
    public String text;
}
