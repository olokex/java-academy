package com.devcors.javaacademy.lesson5.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextConfiguration {

    @Value("${messages.stringText1}")
    private String stringText1;
    @Value("${messages.stringText2}")
    private String stringText2;

    public String getStringText1() {
        return stringText1;
    }

    public void setStringText1(String stringText1) {
        this.stringText1 = stringText1;
    }

    public String getStringText2() {
        return stringText2;
    }

    public void setStringText2(String stringText2) {
        this.stringText2 = stringText2;
    }
}
