package org.nistagram.campaignmicroservice.service.impl;

import org.nistagram.campaignmicroservice.data.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentlyLoggedUserService {
    public static User getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof User) {
            User connectedAgentAccount = ((User) object).getConnectedAgentAccount();
            if (connectedAgentAccount != null) return connectedAgentAccount;
            return (User) object;
        }
        return null;
    }
}
