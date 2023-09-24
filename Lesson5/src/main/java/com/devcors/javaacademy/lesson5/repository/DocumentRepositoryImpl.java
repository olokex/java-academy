package com.devcors.javaacademy.lesson5.repository;

import com.devcors.javaacademy.lesson5.configuration.TextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {

    @Autowired
    private TextConfiguration textConfiguration;

    @Value("${messages.integerValue}")
    private Integer integerValue;

    @Override
    public String retrieveConcatenatedText() {
        return MessageFormat.format("{0} and {1}", textConfiguration.getStringText1(), textConfiguration.getStringText2());
    }

    @Override
    public Integer retrieveNumber() {
        return integerValue;
    }
}
