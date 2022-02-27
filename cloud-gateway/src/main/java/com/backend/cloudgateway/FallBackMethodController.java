package com.backend.cloudgateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/userServiceFallBack")
    public String userServiceFallBackMethod() {
        return "user service down !!";
    }

    @GetMapping("/collectionServiceFallBack")
    public String collectionServiceFallBackMethod() {
        return "collection service down !!";
    }

    @GetMapping("/linkServiceFallBack")
    public String linkServiceFallBackMethod() {
        return "link service down !!";
    }
}

