package com.ai.gemini_chat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class QnAService {
    //Access to APIKey and URL [Gemini]
    @Value("${gemini.api.url}")
    private String gemini_Api_Url;

    @Value("${gemini.api.key}")
    private String gemini_Api_Key;

    private final WebClient webClient;

    public QnAService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public String getAnswer(String question) {
        //Construct the request payload

        //                {"contents": [{
//    "parts":[{"text": "Explain how AI is gonna rule the future"}]
//    }]}

        Map<String , Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts" , new Object[] {
                                Map.of("text" , question)
                        })
                }
        );


        //MAke API Call
        String response = webClient.post()
                .uri(gemini_Api_Url + gemini_Api_Key)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //Return Response
        return response;
    }
}
