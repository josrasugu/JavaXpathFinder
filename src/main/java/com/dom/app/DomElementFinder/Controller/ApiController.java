package com.dom.app.DomElementFinder.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping(value = "/generate")
    public String getPage(){
        xpathController xpath = new xpathController();
        xpath.xpathProcess();

        return "App up and running";
    }
}