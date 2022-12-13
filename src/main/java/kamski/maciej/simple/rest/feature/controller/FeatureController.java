package kamski.maciej.simple.rest.feature.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeatureController {

    @GetMapping("/status/ping")
    public String getPong() {
        return "pong";
    }
}
