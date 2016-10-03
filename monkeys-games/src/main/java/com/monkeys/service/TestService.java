package com.monkeys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by H on 2016. 9. 29..
 */

@Service("testService")
public class TestService {
    private Logger log = LoggerFactory.getLogger(getClass());

    private int num;

    @PostConstruct
    public void init() {
        log.info("TestService PostConstruct");

        num = 1;
    }

    public int getNum() {
        return num++;
    }
}
