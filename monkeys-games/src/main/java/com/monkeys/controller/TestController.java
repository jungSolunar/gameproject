package com.monkeys.controller;

import com.monkeys.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

/**
 * Created by H on 2016. 9. 19..
 */

@Controller
public class TestController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestService testService;

    @PostConstruct
    public void init() {
        log.info("TestController PostConstruct");
    }

    @RequestMapping(value = "test")
    public ModelAndView test() {
        log.info("request test");
        return new ModelAndView("test", "data", testService.getNum());
    }
}
