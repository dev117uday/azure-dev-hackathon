package com.backend.cloudgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    private final Logger logger = LoggerFactory.getLogger(FallBackMethodController.class);

    @GetMapping("/userServiceFallBack")
    public String userServiceFallBackMethod() {
        logger.error("user service down :: userServiceFallBackMethod");
        return "user service down !!";
    }

    @GetMapping("/collectionServiceFallBack")
    public String collectionServiceFallBackMethod() {
        logger.error("collection service down :: collectionServiceFallBackMethod");
        return "collection service down !!";
    }

    @GetMapping("/linkServiceFallBack")
    public String linkServiceFallBackMethod() {
        logger.error("link service down :: linkServiceFallBackMethod");
        return "link service down !!";
    }
}

