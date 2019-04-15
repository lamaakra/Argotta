package com.example.ahmad.chat_3.models.requests;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("id")
    public String userID;

    @SerializedName("display_name")
    public String username;

    @SerializedName("lang")
    public String lang;

    @SerializedName("lang_code")
    public String langCode;

    @SerializedName("full_name")
    public String fullName;

    @SerializedName("email")
    public String email;
}
