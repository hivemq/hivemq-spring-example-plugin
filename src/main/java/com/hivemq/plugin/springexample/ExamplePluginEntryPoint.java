/*
 * Copyright 2016 dc-square GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hivemq.plugin.springexample;

import com.hivemq.plugin.springexample.callbacks.StartCallback;
import com.hivemq.spi.PluginEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This is the main class of the plugin, which is instanciated during the HiveMQ start up process.
 */
public class ExamplePluginEntryPoint extends PluginEntryPoint {

    Logger log = LoggerFactory.getLogger(ExamplePluginEntryPoint.class);

    //Injected by Guice, managed by Spring, so this needs to be named injection
    @Inject
    @Named("systemMessage")
    private SystemMessage systemMessage;

    @Inject
    @Named("systemMessage2")
    private SystemMessage systemMessage2;

    @Inject
    @Named("systemMessage3")
    private SystemMessage systemMessage3;

    @Inject
    @Named("startCallback")
    private StartCallback startCallback;

    /**
     * This method is executed after the instantiation of the whole class. It is used to initialize
     * the implemented callbacks and make them known to the HiveMQ core.
     */
    @PostConstruct
    public void postConstruct() {

        //Default message
        log.info("Message 1: {}", systemMessage.getMessage());

        //Special messages created in SpringConfiguration
        log.info("Message 2: {}", systemMessage2.getMessage());
        log.info("Message 3: {}", systemMessage3.getMessage());

        getCallbackRegistry().addCallback(startCallback);
    }

}
