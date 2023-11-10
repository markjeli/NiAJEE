package com.jelinski.niajee.user.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutUserRequest {

    /**
     * User's login.
     */
    private String login;

    /**
     * User's name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birthdate.
     */
    private LocalDate birthDate;

    /**
     * User's password.
     */
    private String password;

    /**
     * User's email.
     */
    private String email;

}
