package com.example.financialInclusion.controller;

import com.example.financialInclusion.models.AzurePromptRequest;
import com.example.financialInclusion.models.AzurePromptResponse;
import com.example.financialInclusion.service.AzureAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
public class AzureAiController {

    private final AzureAiService azureAiService;

    public AzureAiController(AzureAiService azureAiService) {
        this.azureAiService = azureAiService;
    }

    @PostMapping("/prompt")
    public ResponseEntity<AzurePromptResponse> getResponseFromAzure(@RequestBody AzurePromptRequest request) {
        String response = azureAiService.getCompletion(request.getPrompt());
        return ResponseEntity.ok(new AzurePromptResponse(response));
    }
}
