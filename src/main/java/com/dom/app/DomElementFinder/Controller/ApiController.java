package com.dom.app.DomElementFinder.Controller;
import com.dom.app.DomElementFinder.Forms.AddXpathForm;
import com.dom.app.DomElementFinder.Forms.GenerateXpathForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ApiController {
    @PostMapping(value = "/generate")
    public String generatePath(@RequestBody GenerateXpathForm generateXpathForm){
        xpathController xpath = new xpathController();
        xpath.url = generateXpathForm.url;
        xpath.path = generateXpathForm.path;
        xpath.xpathProcess();

        return "App up and running";
    }

    @PostMapping(value = "/save-path")
    public String saveXpath(@RequestBody AddXpathForm addXpathForm){
        xpathController xpath = new xpathController();
        xpath.url = addXpathForm.url;
        xpath.path = addXpathForm.path;
        xpath.addXpath();

        return "Path saved";
    }

}
