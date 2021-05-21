package com.decagon.obamax.kanban.payload;

import com.decagon.obamax.kanban.model.User;
import lombok.Data;

@Data
public class UserResponse {
    private User user;
    private boolean successful;
    private String message;
}
