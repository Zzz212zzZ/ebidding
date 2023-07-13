package com.ebidding.gpt.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data

@AllArgsConstructor
public class ChatRequestDTO {
    private final String model = "gpt-3.5-turbo";
    private Boolean stream = true;
    private List<SingleMessageDTO> messages;

    public ChatRequestDTO() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(String role, String content) {
        this.messages.add(new SingleMessageDTO(role, content));
    }

}
