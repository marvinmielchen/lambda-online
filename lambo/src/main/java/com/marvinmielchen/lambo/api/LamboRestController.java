package com.marvinmielchen.lambo.api;


import com.marvinmielchen.lambo.Lambo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LamboRestController {

    @GetMapping(value = "syntax_check", produces = "application/json")
    public List<String> syntaxCheck(@RequestParam("sourcecode") String sourcecode) {
        return Lambo.syntaxCheck(sourcecode);
    }

    @GetMapping(value = "beta_reduction", produces = "application/json")
    public List<String> betaReduction(@RequestParam("sourcecode") String sourcecode) {
        return Lambo.betaReduction(sourcecode);
    }

    @GetMapping(value = "statement_substitution", produces = "application/json")
    public String statementSubstitution(@RequestParam("sourcecode") String sourcecode) {
        return "{\"status\": \"ok\"}";
    }

    //status endpoint
    @GetMapping(value = "status", produces = "application/json")
    public String status() {
        return "{\"status\": \"ok\"}";
    }
}
