package com.kuangkuang.kuangkuang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiChatController {
    private final ChatClient chatClient;
    @GetMapping(value = "/chat",produces = "text/html;charset=utf-8")
    public Flux<String> chat(String prompt){
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }
}
