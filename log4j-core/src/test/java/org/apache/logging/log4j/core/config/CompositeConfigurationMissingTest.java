/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CompositeConfigurationMissingTest {

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("log4j2.configurationFile", "classpath:log4j-comp-logger-root.xml,log4j-does-not-exist.json");
    }

    @Test
    public void testMissingConfig() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

        final AbstractConfiguration config = (AbstractConfiguration) ctx.getConfiguration();
        assertNotNull("No configuration returned", config);
        //Test for Root log level override
        assertEquals("Expected Root logger log level to be ERROR", Level.ERROR, config.getRootLogger().getLevel());

        //Test for no cat2 level override
        final LoggerConfig cat2 = config.getLogger("cat2");
        assertEquals("Expected cat2 log level to be INFO", Level.DEBUG, cat2.getLevel());
    }
}
