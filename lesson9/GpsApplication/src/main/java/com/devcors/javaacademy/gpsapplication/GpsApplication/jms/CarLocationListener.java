package com.devcors.javaacademy.gpsapplication.GpsApplication.jms;

import com.devcors.javaacademy.gpsapplication.GpsApplication.data.entity.CarLocation;
import com.devcors.javaacademy.gpsapplication.GpsApplication.data.repository.CarLocationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLocationListener {

    private final ObjectMapper objectMapper;
    private final CarLocationRepository carLocationRepository;

    @RabbitListener(queues = "GpsLocationQueue")
    public void listen(String message) throws JsonProcessingException {
        try {
            CarLocation carLocation = objectMapper.readValue(message, CarLocation.class);
            carLocationRepository.save(carLocation);
        } catch (JsonProcessingException e) {
            log.error("unable to convert", e);
        }
    }
}
