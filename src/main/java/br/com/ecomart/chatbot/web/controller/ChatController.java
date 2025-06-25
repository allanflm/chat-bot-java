package br.com.ecomart.chatbot.web.controller;

import br.com.ecomart.chatbot.domain.service.ChatBotService;
import br.com.ecomart.chatbot.web.dto.PerguntaDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Controller
@RequestMapping({"/", "chat"})
public class ChatController {

    private static final String PAGINA_CHAT = "chat";


    private ChatBotService service;


    public ChatController(ChatBotService service) {
        this.service = service;
    }

    @GetMapping
    public String carregarPaginaChatbot() {
        return PAGINA_CHAT;
    }

    @PostMapping
    @ResponseBody
    public ResponseBodyEmitter responderPergunta(@RequestBody PerguntaDto dto) {
        var fluxoResposta = service.responderPergunta(dto.pergunta());
        var emitter = new ResponseBodyEmitter();

        fluxoResposta.subscribe(chunk -> {
            var token = chunk.getChoices().get(0).getMessage().getContent();
            if(token != null){
                emitter.send(token);
            }
        }, emitter::completeWithError,
                emitter::complete);

        return emitter;

    }

    @GetMapping("limpar")
    public String limparConversa() {
        return PAGINA_CHAT;
    }

}
