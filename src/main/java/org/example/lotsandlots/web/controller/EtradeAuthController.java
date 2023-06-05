package org.example.lotsandlots.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/etrade")
public class EtradeAuthController {

    private static final Logger LOG = LoggerFactory.getLogger(EtradeAuthController.class);

    @GetMapping
    public void foo() {
        LOG.info("woot");
    }

    @GetMapping("/{verifier}")
    public void foo2(@PathVariable String verifier) {
        LOG.info("woot {}", verifier);
    }
}
