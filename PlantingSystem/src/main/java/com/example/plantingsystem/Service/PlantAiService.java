package com.example.plantingsystem.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PlantAiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;

    public PlantAiService(@Value("${openai.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    private String askChat(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", prompt);
        messages.add(userMsg);

        body.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, requestEntity, Map.class);

        Map responseBody = responseEntity.getBody();
        if (responseBody == null) {
            return "AI did not return a response.";
        }

        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        if (choices == null || choices.isEmpty()) {
            return "AI returned no choices.";
        }

        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        if (message == null) {
            return "AI returned an empty message.";
        }

        Object content = message.get("content");
        return content != null ? content.toString() : "AI returned no content.";
    }


    public String getPlantCareAdvice(String plantName) {
        String prompt = """
                You are a gardening assistant.
                Explain in clear, simple steps how to take care of this plant after it has been planted, and list some simple resources:

                Plant: %s

                Mention:
                - watering
                - sunlight
                - soil/fertilizer
                - basic tips.
                """.formatted(plantName);

        return askChat(prompt);
    }


    public String suggestPlantsByLocationAndWeather(String text) {
        String prompt = """
                You are a gardening assistant.
                Suggest some good plants to grow by given location and weather: %s.

                Give a short bullet list.
                For each plant, give a short reason why it fits that location and weather.
                """.formatted(text);

        return askChat(prompt);
    }


    public String getPlantInfo(String plantName) {
        String prompt = """
                Give a friendly introduction and useful facts about this plant: %s.

                Include:
                - what it is
                - where it usually grows
                - typical size
                - any interesting or useful facts.
                - provide at least one common question about the plant, and then give the answer after.
                
                Organize the content so a reader wont be bored.
                """.formatted(plantName);

        return askChat(prompt);
    }


    public String getSupplierSuggestionsByLocation(String location) {
        String prompt = """
                You are helping a tree-planting company.
                They want to find good plant suppliers in this area: %s.

               
                explain:
                - what types of suppliers to look for (nurseries, wholesalers, etc.)
                - what qualities or features to look for
                - any tips specific to choosing suppliers in that location.
                
                after that suggest known suppliers (stores) in that area, you can base it of results on google map.
                """.formatted(location);

        return askChat(prompt);
    }
}