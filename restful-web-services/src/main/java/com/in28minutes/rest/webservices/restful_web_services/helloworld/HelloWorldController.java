package com.in28minutes.rest.webservices.restful_web_services.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
;

//REST API
@RestController
@RequestMapping
public class HelloWorldController {

	@GetMapping(path="/basicauth")
    public String basicauthcheck() {
        return "Success"; 
    }

	
    // Simple String Response
    @GetMapping(path="/hello-world")
    public String helloWorld() {
        return "Hello World"; 
    }

    // Returns a Java Bean as JSON
    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World"); 
    }

    // Path Variable Example
    @GetMapping(path="/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name)); 
    }
    
}
