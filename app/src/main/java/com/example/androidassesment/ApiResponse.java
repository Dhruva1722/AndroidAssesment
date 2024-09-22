package com.example.androidassesment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("choices")
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {

        @SerializedName("message")
        private Message message;

        public Message getMessage() {
            return message;
        }

        public static class Message {

            @SerializedName("content")
            private String content;  // Change to String since it's a JSON string

            public String getContent() {
                return content;
            }

        }
    }
}
