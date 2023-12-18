package com.marvinmielchen.lambo.api;


import com.marvinmielchen.lambo.Lambo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LamboRestController {

    @GetMapping(value = "syntax", produces = "application/json", consumes = "text/plain")
    public List<String> syntaxCheck(@RequestBody String sourcecode) {
        return Lambo.syntaxCheck(sourcecode);
    }

    @GetMapping(value = "reduction", produces = "application/json", consumes = "text/plain")
    public List<String> betaReduction(@RequestBody String sourcecode) {
        return Lambo.betaReduction(sourcecode);
    }

    //status endpoint
    @GetMapping(value = "status", produces = "application/json")
    public String status() {
        return "{\"status\": \"ok\"}";
    }
}
