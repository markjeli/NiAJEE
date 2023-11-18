package com.jelinski.niajee.chat.view;

import com.jelinski.niajee.component.ModelFunctionFactory;
import com.jelinski.niajee.push.context.PushMessageContext;
import com.jelinski.niajee.push.dto.Message;
import com.jelinski.niajee.user.model.UserModel;
import com.jelinski.niajee.user.model.UsersModel;
import com.jelinski.niajee.user.service.UserService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * View bean for rendering chat.
 */
@SessionScoped
@Named
public class Chat implements Serializable {

    /**
     * Context for sending push messages.
     */
    private final PushMessageContext pushMessageContext;

    /**
     * Security context
     */
    private final SecurityContext securityContext;

    /**
     * Service for managing users.
     */
    private UserService userService;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * Message to be sent.
     */
    @Getter
    @Setter
    private String message;

    /**
     * Users list exposed to the view.
     */
    @Getter
    private List<UserModel> users;

    /**
     * User to send message to.
     */
    @Getter
    @Setter
    private UserModel userToSend;

    /**
     * @param pushMessageContext context for sending push messages
     * @param factory factory producing functions for conversion between models and entities
     * @param securityContext    security context
     */
    @Inject
    public Chat(
            PushMessageContext pushMessageContext,
            ModelFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext
    ) {
        this.pushMessageContext = pushMessageContext;
        this.factory = factory;
        this.securityContext = securityContext;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void init() {
        userToSend = UserModel.builder()
                .id(UUID.randomUUID())
                .login("All")
                .build();
        users = userService.findAll().stream()
                .map(factory.userToModel())
                .collect(Collectors.toList());
        users.add(0,  userToSend);
    }

    public void sendMessage() {
        if (userToSend == null || Objects.equals(userToSend.getLogin(), "All")) {
            pushMessageContext.notifyAll(Message.builder()
                    .from(securityContext.getCallerPrincipal().getName())
                    .content(message)
                    .build());
        } else {
            pushMessageContext.notifyUser(Message.builder()
                    .from(securityContext.getCallerPrincipal().getName())
                    .content(message)
                    .build(), userToSend.getLogin());
        }
        message = "";
    }

}
