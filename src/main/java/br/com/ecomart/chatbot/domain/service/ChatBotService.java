package br.com.ecomart.chatbot.domain.service;

import br.com.ecomart.chatbot.infra.openai.DadosRequisicaoChatCompletion;
import br.com.ecomart.chatbot.infra.openai.OpenAIClient;
import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import io.reactivex.Flowable;
import org.springframework.stereotype.Service;

@Service

public class ChatBotService {

    private OpenAIClient client;

    public ChatBotService(OpenAIClient client) {
        this.client = client;
    }
    public Flowable<ChatCompletionChunk> responderPergunta(String pergunta){
        var promptSistem = "Você é um chatbot de atendimento a clientes de um ecommerce e deve responder apenas perguntas relacionadas com o ecommerce";
        var dados = new DadosRequisicaoChatCompletion(promptSistem, pergunta);
        return client.enviarRequisicaoChatCompletion(dados);
    }
}
