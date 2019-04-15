package com.example.ahmad.chat_3.mvp.travelMode;


import com.example.ahmad.chat_3.mvp.BasePresenter;

public class TravelModeContract {

    public static abstract class Presenter extends BasePresenter<View> {
        public abstract void translate(String text, String langFrom, String langTo);
    }

    public interface View {
        void displayTranslation(String translation);
        void displayError(String error);
    }
}
