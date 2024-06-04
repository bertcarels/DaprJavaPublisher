package com.kaboutersoft.group.gtsciais.demo.service.publish;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.kaboutersoft.group.gtsciais.demo.model.Customer;
import com.kaboutersoft.group.gtsciais.demo.repository.CustomerRepository;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Service
public class CustomerServicePubSub {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServicePubSub.class);
    
    private DaprClient client = new DaprClientBuilder().build();
    @Value("${kab.dapr.pubsub.topic}")
    private String TOPIC_NAME;
    @Value("${kab.dapr.pubsub.name}")
    private String PUBSUB_NAME;
    
    @Value("${kab.dapr.pubsub.raw}")
    private String PUBSUB_RAW;
    
    @Value("${kab.dapr.pubsub.format}")
    private String PUBSUB_FORMAT;

    Map<String, String> metadata = new HashMap<>();

    
    
    
    
    @Autowired
    public CustomerServicePubSub() {
        
    }

    @PostConstruct
    public void init() {
        logger.info("Dapr Pubsub name: " + PUBSUB_NAME);
        logger.info("Dapr Topic name: " + TOPIC_NAME);
        logger.info("Dapr Pubsub raw: " + PUBSUB_RAW);
        logger.info("Dapr Pubsub format: " + PUBSUB_FORMAT);

        if (!PUBSUB_FORMAT.equals("")) {
            metadata.put("valueSchemaType", PUBSUB_FORMAT);
        }
        if (!PUBSUB_RAW.equals("")) {
            metadata.put("rawPayload", "true");
        }
        
        logger.info("Metadata: {}", metadata);

        
    }


    public Customer publishCustomerEvent (Customer customer) {

        if (metadata.size()==0) {
            client.publishEvent(
                PUBSUB_NAME,
                TOPIC_NAME,
                customer
                ).block();
            
            logger.info("Publishing CloudEvent : " + customer.getId());

        } else {
            client.publishEvent(
                PUBSUB_NAME,
                TOPIC_NAME,
                customer, 
                metadata
                ).block();

                logger.info("Publishing with metadata: " + PUBSUB_RAW + " " + PUBSUB_FORMAT +"Event : " + customer.getId());

        }

       
        
        return customer;    
    }
 }
