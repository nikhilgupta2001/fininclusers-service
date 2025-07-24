package com.example.financialInclusion.service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.ai.openai.models.ChatResponseMessage;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.stereotype.Service;
import com.azure.ai.openai.OpenAIServiceVersion;


import java.util.Arrays;
import java.util.List;

@Service
public class AzureAiServiceImpl implements AzureAiService {

    private static final String API_KEY = "DEiBOTM8WWZhfnUK1qoccUvXpt79dpoGXglmvT2Z6MPCXGkON3FMJQQJ99BGACHrzpqXJ3w3AAAAACOGgMA2"; // 🔐 Replace in prod
    private static final String ENDPOINT = "https://finincluser-2-resource.cognitiveservices.azure.com/";
    private static final String DEPLOYMENT_NAME = "o3-mini";


    @Override
    public String getCompletion(String prompt) {
        OpenAIClient client = new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(API_KEY))
                .endpoint(ENDPOINT)
                .serviceVersion(OpenAIServiceVersion.getLatest()) // THIS LINE IS MANDATORY
                .buildClient();

        // Rest of your existing code remains unchanged
        try {
            List<ChatRequestMessage> chatMessages = Arrays.asList(
                    new ChatRequestSystemMessage("You are a helpful assistant."),
                    new ChatRequestUserMessage(prompt)
            );

            ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatMessages);
            chatCompletionsOptions.setMaxTokens(800);
            chatCompletionsOptions.setTemperature(1.0);
            chatCompletionsOptions.setTopP(1.0);
            chatCompletionsOptions.setFrequencyPenalty(0.0);
            chatCompletionsOptions.setPresencePenalty(0.0);

            ChatCompletions chatCompletions = client.getChatCompletions(DEPLOYMENT_NAME, chatCompletionsOptions);

            StringBuilder responseBuilder = new StringBuilder();
            for (ChatChoice choice : chatCompletions.getChoices()) {
                ChatResponseMessage message = choice.getMessage();
                responseBuilder.append(message.getContent()).append("\n");
            }

            return responseBuilder.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error from Azure OpenAI: " + e.getMessage();
        }
    }

}
