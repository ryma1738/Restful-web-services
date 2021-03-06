package com.rest.webservices.restfulwebservices.helloWorld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class helloWorldController {

    @Autowired
    private MessageSource ms;
// relies on messages.properties

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world/pathvar/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean("Hello World" + ", " + name);
    }

    @GetMapping(path = "/hello-world-international")
    public String helloWorldInternational(
            @RequestHeader(name="Accept-Language", required=false) Locale locale
    ) {

        return ms.getMessage("good.morning.message", null, locale);
    }
}
