package com.askhaev.VaadinAll;

import com.vaadin.ui.Label;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HelloWorldLabel extends Label {

    @PostConstruct
    public void init(){
        setValue("Hello world!");
    }
}
