package com.monkeys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @PostConstruct
    public void init() {
        log.info("TestController PostConstruct");
    }

    @RequestMapping(value = "test")
    public ModelAndView test() {
        log.info("request test");
        return new ModelAndView("test");
    }

    @RequestMapping(value = "HelloWorld")
    public ModelAndView cocostest() {
        ModelAndView mav = new ModelAndView();
        return new ModelAndView("cocos-test/HelloWorld.html");
    }
}
