package com.example.ahmad.chat_3.mvp.travelMode;

import com.example.ahmad.chat_3.api.ChatApi;
import com.example.ahmad.chat_3.models.requests.TranslationRequest;
import com.example.ahmad.chat_3.models.responses.TranslationResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelModePresenter extends TravelModeContract.Presenter {

    private ChatApi chatApi;

    public TravelModePresenter(ChatApi chatApi) {
        this.chatApi = chatApi;
    }

    @Override
    public void translate(String text, String langFrom, String langTo) {
        TranslationRequest requestModel = new TranslationRequest();
        requestModel.text = text;
        requestModel.langCodeFrom = langFrom;
        requestModel.langCodeTo = langTo;

        chatApi.translate(requestModel).enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                if (isViewAttached()) {
                    if (response.isSuccessful()) {
                        TranslationResponse translationResponse = response.body();
                        if (translationResponse.translation != null) {
                            getView().displayTranslation(translationResponse.translation);
                        } else {
                            getView().displayError(translationResponse.reason);
                        }
                    } else {
                        getView().displayError("An error has occurred");
                    }
                }
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable t) {
                if (isViewAttached()) {
                    getView().displayError("An error has occurred");
                }
            }
        });
    }
}
