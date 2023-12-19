package com.marvinmielchen.lambo.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ErrorDTO {
    private String message;
    private int line;
}
