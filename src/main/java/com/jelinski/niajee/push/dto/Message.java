package com.jelinski.niajee.push.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * WebSocket message representation.
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Message {

    /**
     * Message author.
     */
    private String from;

    /**
     * Message content.
     */
    private String content;

}

