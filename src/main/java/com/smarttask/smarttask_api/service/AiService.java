package com.smarttask.smarttask_api.service;

import com.smarttask.smarttask_api.model.AiRecommendations;
import com.smarttask.smarttask_api.repository.AiRecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AiService {

    @Value("${groq.api.key:}")
    private String groqApiKey;

    @Value("${groq.model:llama-3.3-70b-versatile}")
    private String groqModel;

    @Autowired
    private AiRecommendationsRepository aiRecommendationsRepository;

    private String callAi(String prompt) {
        if (groqApiKey == null || groqApiKey.isBlank()) {
            return "ERROR: GROQ_API_KEY is not configured";
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.groq.com/openai/v1/chat/completions";

            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);

            Map<String, Object> body = new HashMap<>();
            body.put("model", groqModel);
            body.put("messages", List.of(message));
            body.put("max_tokens", 500);
            body.put("temperature", 0.2);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(groqApiKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            Map responseBody = response.getBody();
            List choices = (List) responseBody.get("choices");
            Map firstChoice = (Map) choices.get(0);
            Map messageResponse = (Map) firstChoice.get("message");
            return (String) messageResponse.get("content");

        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private void saveRecommendation(Integer taskId, String username,
            String recommendation) {
        AiRecommendations rec = new AiRecommendations();
        rec.setTaskId(taskId);
        rec.setUsername(username);
        rec.setRecommendation(recommendation);
        rec.setCreatedDate(getCurrentDateTime());
        aiRecommendationsRepository.save(rec);
    }

    public Map<String, Object> rewriteTask(String description,
            Integer taskId, String username) {
        Map<String, Object> response = new HashMap<>();
        String prompt = "You are a corporate task clarity assistant. " +
                "Rewrite the following task description to be clear, " +
                "professional and actionable. Keep it concise. " +
                "Task: " + description;
        String result = callAi(prompt);
        if (taskId != null)
            saveRecommendation(taskId, username, result);
        response.put("success", true);
        response.put("result", result);
        return response;
    }

    public Map<String, Object> summarizeTask(String description,
            Integer taskId, String username) {
        Map<String, Object> response = new HashMap<>();
        String prompt = "Summarize the following task description in " +
                "2-3 clear sentences for quick understanding: " + description;
        String result = callAi(prompt);
        if (taskId != null)
            saveRecommendation(taskId, username, result);
        response.put("success", true);
        response.put("result", result);
        return response;
    }

    public Map<String, Object> suggestDeadline(String title,
            String description,
            String priority) {
        Map<String, Object> response = new HashMap<>();
        String prompt = "You are a project management assistant. " +
                "Based on this task, suggest a realistic deadline in days. " +
                "Reply with ONLY a number (e.g. 5). " +
                "Task title: " + title + ". " +
                "Description: " + description + ". " +
                "Priority: " + priority;
        String result = callAi(prompt);
        int suggestedDays = extractDays(result);

        response.put("success", true);
        response.put("suggestedDays", String.valueOf(suggestedDays));
        response.put("message", "Complete this task in approximately "
                + suggestedDays + " days");
        return response;
    }

    private int extractDays(String modelOutput) {
        if (modelOutput == null) {
            return 7;
        }

        Matcher matcher = Pattern.compile("(\\d+)").matcher(modelOutput);
        if (matcher.find()) {
            try {
                int parsed = Integer.parseInt(matcher.group(1));
                return Math.max(parsed, 1);
            } catch (NumberFormatException ignored) {
                return 7;
            }
        }
        return 7;
    }

    public List<AiRecommendations> getRecommendations(Integer taskId) {
        return aiRecommendationsRepository.findByTaskId(taskId);
    }
}
