package com.devcors.javaacademy.lesson5.facade;

import com.devcors.javaacademy.lesson5.service.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutputFacadeImpl implements OutputFacade {

    @Autowired
    private OutputService numericTextOutputService;

    @Autowired
    private OutputService stringOutputService;

    @Override
    public String outputText() {
        return stringOutputService.output();
    }

    @Override
    public String outputNumber() {
        return numericTextOutputService.output();
    }
}
