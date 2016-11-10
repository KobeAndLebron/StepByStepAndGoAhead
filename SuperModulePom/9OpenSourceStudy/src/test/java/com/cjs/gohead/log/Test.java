package com.cjs.gohead.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Created by chenjingshuai on 16-11-8.
 */
public class Test {
    static Logger logger = Logger.getLogger(Test.class);

    public static void main(String[] args)
    {
        int i = 0;
        PropertyConfigurator.configure("/home/chenjingshuai/workspace/StepByStepAndGoAhead/SuperModulePom/9OpenSourceStudy/src/test/java/com/cjs/gohead/log/log4j.properties");
        // DOMConfigurator.configure("/home/chenjingshuai/workspace/StepByStepAndGoAhead/SuperModulePom/9OpenSourceStudy/src/test/java/com/cjs/gohead/log/log4j.xml");
        while (i < 10) {
            //DOMConfigurator is used to configure logger from xml configuration file

            //Log in console in and log file
            logger.info("Log4j appender configuration is successful !!");
            i++;
        }
    }
}
