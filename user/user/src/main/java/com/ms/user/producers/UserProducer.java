package com.ms.user.producers;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.ms.user.constants.Constants.CADASTRO_REALIZADO_COM_SUCESSO;
import static com.ms.user.constants.Constants.SEJA_BEM_VINDO;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel) {
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject(CADASTRO_REALIZADO_COM_SUCESSO);
        emailDto.setText(userModel.getName() + SEJA_BEM_VINDO);

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

}
