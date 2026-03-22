package org.example.eventProducer;

import org.example.models.UserInfoDTO;
import org.springframework.stereotype.Service;

import lombok.Value;

@Service
public class UserInfoProducer {
    // private final KafkaTemplate<String, UserInfoDto> kafkaTemplate;

    // @Value("${spring.kafka.topic.name")
    // private String topicName;

    // // @Autowired
    // // UserInfoProducer(KafkaTemplate<String, UserInfoDTO> kafkaTemplate){
    // // this.kafkaTemplate = kafkaTemplate;
    // // }

    // public void sendEventToKafka(UserInfoDTO userInfoDTO){
    //     Message<UserInfoDTO> message = MessageBuilder.withPayoad(userInfoDTO)
    //     .setHeader(KafkaHeaders.TOPIC_NAME).build();
    //     kafkaTemplate.send(message);
    // }

}