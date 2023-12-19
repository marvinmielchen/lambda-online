package com.marvinmielchen.lambo.api;


import com.marvinmielchen.lambo.Lambo;
import com.marvinmielchen.lambo.lexicalanalysis.LexingError;
import com.marvinmielchen.lambo.semanticanalysis.RuntimeError;
import com.marvinmielchen.lambo.syntacticanalysis.ParseError;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class LamboRestController {

    @CrossOrigin(origins = "*")
    @PostMapping(value = "syntax", produces = "application/json", consumes = "text/plain")
    public LamboDTO syntaxCheck(@RequestBody String sourcecode) {
        try {
            Lambo.syntaxCheck(sourcecode);
            return new LamboDTO(
                    null,
                    sourcecode
            );
        } catch (LexingError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getLine()),
                    sourcecode
            );
        } catch (ParseError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getToken().getLine()),
                    sourcecode
            );
        } catch (RuntimeError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getLine()),
                    sourcecode
            );
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "reduction", produces = "application/json", consumes = "text/plain")
    public LamboDTO betaReduction(@RequestBody String sourcecode) {
        try {
            String processedSrc = Lambo.betaReduction(sourcecode);
            return new LamboDTO(
                    null,
                    processedSrc
            );
        } catch (LexingError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getLine()),
                    sourcecode
            );
        } catch (ParseError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getToken().getLine()),
                    sourcecode
            );
        } catch (RuntimeError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getLine()),
                    sourcecode
            );
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "substitution", produces = "application/json", consumes = "text/plain")
    public LamboDTO statementSubstitution(@RequestBody String sourcecode) {
        try {
            String processedSrc = Lambo.statementSubstitution(sourcecode);
            return new LamboDTO(
                    null,
                    processedSrc
            );
        } catch (LexingError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getLine()),
                    sourcecode
            );
        } catch (ParseError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getToken().getLine()),
                    sourcecode
            );
        } catch (RuntimeError e) {
            return new LamboDTO(
                    new ErrorDTO(e.getMessage(), e.getLine()),
                    sourcecode
            );
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "status", produces = "application/json")
    public String status() {
        return "{\"status\": \"ok\"}";
    }
}
