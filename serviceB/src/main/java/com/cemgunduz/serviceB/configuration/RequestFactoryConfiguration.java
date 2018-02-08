package com.cemgunduz.serviceB.configuration;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Created by cem on 07/02/18.
 */

@Configuration
public class RequestFactoryConfiguration {

    @Bean
    LoadBalancerRequestFactory loadBalancerRequestFactory(LoadBalancerClient loadBalancer) {
        LoadBalancerRequestTransformer bbRequestTransformer = new LoadBalancerRequestTransformer() {

            @Override
            public HttpRequest transformRequest(HttpRequest request, ServiceInstance instance) {

                String contextRoot = instance.getMetadata().get("context-root");
                return new HttpRequestWrapper(request) {

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
            }
        };

        return new LoadBalancerRequestFactory(loadBalancer, Arrays.asList(bbRequestTransformer));
    }
}
