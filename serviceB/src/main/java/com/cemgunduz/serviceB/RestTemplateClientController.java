package com.cemgunduz.serviceB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cem on 02/02/18.
 */

@RestController
public class RestTemplateClientController {

    @Bean
    LoadBalancerRequestFactory loadBalancerRequestFactory(LoadBalancerClient loadBalancer)
    {
        LoadBalancerRequestTransformer bbRequestTransformer = new LoadBalancerRequestTransformer() {

            @Override
            public HttpRequest transformRequest(HttpRequest request, ServiceInstance instance) {

                String contextRoot = instance.getMetadata().get("context-root");

                HttpRequest httpRequest = new HttpRequestWrapper(request){

                    @Override
                    public URI getURI() {

                        URI uri = super.getURI();

                        String pathWithContext = contextRoot.concat(uri.getPath());
                        try {
                            return new URI(uri.getScheme(),
                                    uri.getUserInfo(),
                                    uri.getHost(),
                                    uri.getPort(),
                                    pathWithContext,
                                    uri.getQuery(),
                                    uri.getFragment());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        return uri;
                    }
                };

                return httpRequest;
            }
        };

        return new LoadBalancerRequestFactory(loadBalancer, Arrays.asList(bbRequestTransformer));
    }

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
