package com.marvinmielchen.lambo.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LamboDTO {
    private ErrorDTO error;
    private String result;
}
