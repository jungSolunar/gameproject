package com.monkeys.games;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by H on 2016. 10. 28..
 */

@Service("rspService")
public class RSPService extends GameService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {
        log.info("rspService init");
    }

    @PreDestroy
    public void destroy() {
        log.info("rspService destroy");
    }

    @Override
    public String getServiceName() {
        return "rspService";
    }
}
