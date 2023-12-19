package com.marvinmielchen.lambo.api;


import com.marvinmielchen.lambo.Lambo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LamboRestController {

    @CrossOrigin(origins = "*")
    @PostMapping(value = "syntax", produces = "application/json", consumes = "text/plain")
    public List<String> syntaxCheck(@RequestBody String sourcecode) {
        return Lambo.syntaxCheck(sourcecode);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "reduction", produces = "application/json", consumes = "text/plain")
    public List<String> betaReduction(@RequestBody String sourcecode) {
        return Lambo.betaReduction(sourcecode);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "status", produces = "application/json")
    public String status() {
        return "{\"status\": \"ok\"}";
    }
}
