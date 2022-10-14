package study.security.basicauthentication.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/greeting")
    public String greeting() {
        return "hello";
    }

    @PostMapping(value = "/greeting")
    public String greeting(@RequestBody String name) {
        return "hello " + name;
    }
}
