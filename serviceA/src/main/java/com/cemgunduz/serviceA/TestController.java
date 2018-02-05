package com.cemgunduz.serviceA;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cem on 02/02/18.
 */

@RestController
public class TestController {

    @Value("${server.servlet-path:root}")
    private String servletPath;

    @RequestMapping("/test")
    public String testEndpoint()
    {
        return "I am BATMAN ! @ ".concat(servletPath);
    }
}
