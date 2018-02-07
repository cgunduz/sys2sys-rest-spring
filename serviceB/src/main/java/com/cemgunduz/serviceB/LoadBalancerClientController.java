package com.cemgunduz.serviceB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by cem on 02/02/18.
 */

@RestController
public class LoadBalancerClientController {

    @Autowired
    private LoadBalancerClient discoveryClient;

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/test")
    public String testEndpoint() throws URISyntaxException {

        String uriString = getUri("serviceA").concat("/test");
        return restTemplate.getForEntity(new URI(uriString), String.class).getBody();
    }

    private String getUri(String serviceName)
    {
        ServiceInstance serviceInstance = discoveryClient.choose(serviceName);
        String scheme = "http://";
        String ipAddr = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String contextRoot = serviceInstance.getMetadata().get("context-root");
        String baseUrl = scheme.concat(ipAddr)
                .concat(":")
                .concat(String.valueOf(port))
                .concat(contextRoot);

        return baseUrl;
    }
}
