/*
 * Copyright (C) 2025-2030 Jidcoo(https://github.com/jidcoo).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lcenhancer.debugger4j;

import io.github.lcenhancer.base.exception.EnhancerException;
import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.utils.StringUtil;
import io.github.lcenhancer.debugger4j.bootstrap.Debugger4jBootstrap;

/**
 * Leetcode debugger for java.
 *
 * @author Jidcoo
 * @since 1.0.0
 */
public abstract class Debugger4j implements LeetcodeJavaDebugEnhancer {

    /**
     * Debugger4j version.
     */
    private static final String VERSION = "1.0.0";

    public static void main(String[] args) throws ClassNotFoundException {
        String AT = obtainATFromStartupArguments(args);
        if (StringUtil.isBlank(AT)) {
            // Depend on java runtime feature.
            AT = System.getProperty("sun.java.command");
        }
        if (Debugger4j.class.getName().equals(AT)) {
            throw new EnhancerException("Cannot start from an abstract Debugger4j.");
        }
        System.out.println("Debugger4j[" + VERSION + "] starting.");
        Debugger4jBootstrap.startup(ClassLoader.getSystemClassLoader().loadClass(AT));
    }

    private static String obtainATFromStartupArguments(String[] args) {
        for (String arg : args) {
            if (!StringUtil.isBlank(arg)) {
                return arg;
            }
        }
        return null;
    }
}
