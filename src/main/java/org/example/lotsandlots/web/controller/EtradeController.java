package org.example.lotsandlots.web.controller;

import org.example.lotsandlots.etrade.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/etrade")
public class EtradeController {

    private static final Logger LOG = LoggerFactory.getLogger(EtradeController.class);

    RestTemplateFactory templateFactory;

    @GetMapping("/auth")
    public void getAuth() {
        LOG.info("woot");
    }

    @GetMapping("/auth/{verifier}")
    public void getAuthWithVerifier(@PathVariable String verifier) {
        LOG.info("woot {}", verifier);
    }

    @GetMapping("/controller/info")
    public void getControllerInfo() {
        templateFactory.info();
    }

    @Autowired
    public void setTemplateFactory(RestTemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
    }
}
