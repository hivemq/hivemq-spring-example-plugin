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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christoph Schäbel
 */
@Configuration
public class SpringConfiguration {

    //Additional instances of SystemMessage which are constructed in a different way
    @Bean(name = "systemMessage2")
    SystemMessage systemMessage2() {
        final SystemMessage systemMessage = new SystemMessage();
        systemMessage.setMessage("System message set in SpringConfiguration");
        return systemMessage;
    }

    @Bean(name = "systemMessage3")
    SystemMessage systemMessage3() {
        final SystemMessage systemMessage = new SystemMessage();
        systemMessage.setMessage("Another system message set in SpringConfiguration");
        return systemMessage;
    }

}
