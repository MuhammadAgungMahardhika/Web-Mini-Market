package com.xa.lookupservice.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xa.lookupservice.models.LookUp;
import com.xa.lookupservice.repositories.LookupRepository;
import com.xa.lookupservice.services.Producer;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final Producer producer;

    @Autowired
    LookupRepository lookupRepository;

    @Autowired
    public KafkaController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic() {
        List<LookUp> lookup = this.lookupRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsoString = objectMapper.writeValueAsString(lookup);
            this.producer.sendMessage(jsoString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
