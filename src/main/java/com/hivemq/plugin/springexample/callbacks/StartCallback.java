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

package com.hivemq.plugin.springexample.callbacks;

import com.google.inject.Inject;
import com.hivemq.plugin.springexample.SystemMessage;
import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.events.broker.OnBrokerStart;
import com.hivemq.spi.callback.exception.BrokerUnableToStartException;
import com.hivemq.spi.message.QoS;
import com.hivemq.spi.message.RetainedMessage;
import com.hivemq.spi.services.RetainedMessageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Example callback implementation managed by Spring
 *
 * @author Christoph Schäbel
 */
@Component
public class StartCallback implements OnBrokerStart {

    private static final Logger log = LoggerFactory.getLogger(StartCallback.class);

    //com.google.inject.Inject is used here to inject a service from HiveMQ through Guice
    //also see com.hivemq.plugin.springexample.ExamplePluginModule.configurePlugin() for
    //how to tell Guice to inject into this instance
    @Inject
    RetainedMessageStore retainedMessageStore;

    //Injected by Spring
    @Autowired
    SystemMessage systemMessage;

    public StartCallback() {
    }

    @Override
    public void onBrokerStart() throws BrokerUnableToStartException {

        log.info("Start callback message: {}", systemMessage.getMessage());

        //Sets a retained message on topic "testtopic" programatically by using HiveMQ's plugin services
        retainedMessageStore.addOrReplace(new RetainedMessage("testtopic", "testpayload".getBytes(), QoS.AT_LEAST_ONCE));
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
