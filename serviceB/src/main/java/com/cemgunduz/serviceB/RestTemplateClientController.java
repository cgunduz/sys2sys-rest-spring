package com.cemgunduz.serviceB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by cem on 02/02/18.
 */

@RestController
public class RestTemplateClientController {

    @Bean
    @LoadBalanced
    private RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test2")
    public String testEndpoint() throws URISyntaxException {

        String uriString = getUri("serviceA").concat("/test");
        return restTemplate.getForEntity(new URI(uriString), String.class).getBody();
    }

    private String getUri(String serviceName) {
        String scheme = "http://";
        String uri = scheme.concat(serviceName);

        return uri;
    }
}
