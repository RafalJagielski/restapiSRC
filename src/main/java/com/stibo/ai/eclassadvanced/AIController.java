package com.stibo.ai.eclassadvanced;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AIController {

    @GetMapping("/queryAI")
    public List<String> queryAI() {
        String endpoint = "https://ai-pakaai613860912109.openai.azure.com/";
        String azureOpenaiKey = "6816ee8ab1d243f0b69befdf757c7597";
        String deploymentOrModelId = "gpt-35-turbo";

        OpenAIClient client = new OpenAIClientBuilder().endpoint(endpoint).credential(new AzureKeyCredential(azureOpenaiKey)).buildClient();

        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage(SYS_MSG));
        chatMessages.add(new ChatRequestUserMessage("{product_name: Auxiliary contact block, 1NO+1NC, screw connection, Contactor, 3p+1NO, 4 kW/400/AC3, 9A, coil 230-240V50/60Hz, screw connection}"));

        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, new ChatCompletionsOptions(chatMessages));

        System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());

        System.out.println("------------------------");

        List<String> results = new ArrayList<>();
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatResponseMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
            results.add(message.getContent());
        }
        System.out.println("------------------------");
        CompletionsUsage usage = chatCompletions.getUsage();
        System.out.printf("Usage: number of prompt token is %d, " +
                "number of completion token is %d, and number of total tokens in request and response is %d.%n",
                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
        return results;
    }

    private final static
    String SYS_MSG = "You are a MDM expert. Your task is to classify products to industry standards (taxonomies).\n" +
        "\n" +
        "Your output should follow the structure:\n" +
        "{\n" +
        "taxonomy: {your output}\n" +
        "}\n" +
        "\n" +
        "Input will be as following:\n" +
        "{\n" +
        "product_name: {name_of_product}\n" +
        "}\n" +
        "\n" +
        "Here's the mapping between existing taxonomies and existing products that you should use to infer taxonomy for the new data. The mapping follows the pattern: {product name} -> {taxonomy}\n" +
        "\n" +
        "Contactor relay, 3NO+2NC, coil 230V50/60Hz -> eClassAdv_10_ADN638007\n" +
        "Contactor relay, 5NO, coil 230V50/60Hz -> eClassAdv_10_ADN638007\n" +
        "Contactor, 3p+1NO+1NC, 4kW/400V/AC3, 9A, coil 230V50/60Hz, screw connection -> eClassAdv_10_ADO120007\n" +
        "Contactor, 4p(2NO+2NC)+1NO+1NC, 25A/AC1, coil 24VDC, lug ring connection -> eClassAdv_10_ADO120007\n" +
        "Contactor, 3p+1NO, 4 kW/400/AC3, 9A, coil 230-240V50/60Hz, screw connection -> eClassAdv_10_ADO120007\n" +
        "Contactor, 3p+1NO, 4kW/400V/AC3, 9A, coil 230-240V50/60Hz, screw connection -> eClassAdv_10_ADO120007\n" +
        "Contactor, 3p+1NO+1NC, 7.5kW/400V/AC3, 18A, coil 230V50Hz, screw connection -> eClassAdv_10_ADO120007\n" +
        "Contactor, 3p+1NO+1NC, 18.5kW/400V/AC3, 38A, coil 230V50Hz, screw connection -> eClassAdv_10_ADO120007\n" +
        "Circuit Breaker NSX160H, +ML2.2M 150A, 3p3d -> eClassAdv_10_ADN710007\n" +
        "Circuit Breaker NSX250H, 250A, 3p, +ML2.2M 150A, 3p3d -> eClassAdv_10_ADN710007\n" +
        "Circuit Breaker NSX630HB2, 630A, 3p, +ML6.3E-M, 500A, 3p3d -> eClassAdv_10_ADN710007\n" +
        "Motor circuit-breaker GV2LE, 3p, 32A, trip magnetic, rocker lever, screw connection -> eClassAdv_10_ADN710007\n" +
        "Motor circuit-breaker GV2ME, 3p, 4-6.3A, push button actuation, spring clamp connection -> eClassAdv_10_ADN710007\n" +
        "Motor circuit-breaker GV2ME, 3p, 6-10A, push button actuation, screw connection -> eClassAdv_10_ADN710007\n" +
        "Motor circuit-breaker GV2P, 3p, 9-14A, rotary actuation, screw connection -> eClassAdv_10_ADN710007\n" +
        "Circuit breaker NSX100N, 100A, 3p, +TMD 100A, 3p3D -> eClassAdv_10_ADN709007\n" +
        "Circuit breaker MTZ1, 1600A, 3p, H1 Icu=42kA/440V, basic switch fix -> eClassAdv_10_ADN709007\n" +
        "Circuit breaker MTZ1, 800A, 3p, L1 Icu=150kA/440V, basic switch fix -> eClassAdv_10_ADN709007\n" +
        "Circuit breaker NS1600H, 1600A, 3p, fixed, +ML2.0 1600A, 3p3D, 70kA -> eClassAdv_10_ADN709007\n" +
        "Circuit breaker NS800N, 800A, 4p, fixed, without control unit, 50kA -> eClassAdv_10_ADN709007\n" +
        "Under-voltage release, 24V50Hz, for GV -> eClassAdv_10_ADO368007\n" +
        "Under-voltage release, 220-240V50Hz, for GV -> eClassAdv_10_ADO368007\n" +
        "Under-voltage release, INRS, 220-240V50Hz, for GV -> eClassAdv_10_ADO368007\n" +
        "Shunt trip, 24V50Hz, for GV -> eClassAdv_10_ADN889007\n" +
        "Shunt trip, 220-240V50Hz, for GV -> eClassAdv_10_ADN889007\n" +
        "Standard auxiliairy contact, circuit breaker status OF-SD-SDE-SDV -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block GVAM, 1CO, fault, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block GVAN, 1NO+1NC, lateral, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block GVAN, 2NO, lateral, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 1NO+1NC, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 2NO, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 1NC, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 4NC, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 1NO, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 1NO+1NC, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 1NO+3NC, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 2NO+2NC, screw connection -> eClassAdv_10_ADN640007 \n" +
        "Auxiliary contact block, 3NO+1NC, screw connection -> eClassAdv_10_ADN640007\n" +
        "Auxiliary contact block, 4NO, screw connection -> eClassAdv_10_ADN640007";
}
