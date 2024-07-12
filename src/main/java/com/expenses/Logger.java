package com.expenses;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Log
@Component
public class Logger  {
    public void logInfo(String message) {
        log.info(message);
    }
    public void logWarning(String message) {
        log.warning(message);
    }

}
