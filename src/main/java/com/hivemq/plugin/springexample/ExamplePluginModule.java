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

import com.google.inject.spring.SpringIntegration;
import com.hivemq.spi.HiveMQPluginModule;
import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.plugin.meta.Information;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is the plugin module class, which handles the initialization and configuration
 * of the plugin. Each plugin need to have a class, which is extending {@link HiveMQPluginModule}.
 * Also the fully qualified name of the class should be present in a file named
 * com.hivemq.spi.HiveMQPluginModule, which has to be located in META-INF/services.
 */
@Information(name = "HiveMQ Spring Example Plugin", author = "Christoph Schaebel", version = "1.0.0")
public class ExamplePluginModule extends HiveMQPluginModule {

    /**
     * This method is provided to execute some custom plugin configuration stuff. Is is the place
     * to execute Google Guice bindings,etc if needed.
     */
    @Override
    protected void configurePlugin() {

        ClassPathXmlApplicationContext ctx = getApplicationContext();

        //Make all instances managed by Spring known to Guice
        SpringIntegration.bindAll(binder(), ctx.getBeanFactory());

        //Make Spring's ApplicationContext known to Guice
        bind(ApplicationContext.class).toInstance(ctx);

        //Tell Guice to inject into the StartCallback instance, which is managed by Spring
        requestInjection(ctx.getBeanFactory().getBean("startCallback"));
    }

    private ClassPathXmlApplicationContext getApplicationContext() {

        //We need to use ClassPathXmlApplicationContext here and pass a ClassLoader because
        //every plugin has his own classloader and Spring won't be able to find the xml file in
        //the default system classloader

        ClassLoader classLoader = this.getClass().getClassLoader();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        ctx.setClassLoader(classLoader);
        ctx.setConfigLocation("spring-context.xml");
        ctx.refresh();
        return ctx;
    }

    /**
     * This method needs to return the main class of the plugin.
     *
     * @return callback priority
     */
    @Override
    protected Class<? extends PluginEntryPoint> entryPointClass() {
        return ExamplePluginEntryPoint.class;
    }
}


