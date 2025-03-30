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

package io.github.lcenhancer.debugger4j.bootstrap;

import io.github.lcenhancer.base.exception.EnhancerException;
import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.utils.AssertUtil;
import io.github.lcenhancer.base.utils.LogUtil;
import io.github.lcenhancer.base.utils.ReflectUtil;
import io.github.lcenhancer.debugger4j.pipeline.PipelineProcessor;
import io.github.lcenhancer.debugger4j.proxy.EnhancerProxyFactory;

/**
 * A bootstrap for Debugger4j.
 *
 * @author Jidcoo
 * @since 1.0.0
 */
public class Debugger4jBootstrap {

    /**
     * <p>Do leetcode debugging enhance process with an <tt>AT</tt> class.</p>
     * <p>I don't want to say much, please take a look at the code.</p>
     *
     * @param AT the <tt>AT</tt> class.
     */
    public static void startup(Class<?> AT) {
        AssertUtil.nonNull(AT, "The class cannot be null.");
        AssertUtil.isTrue(
                ReflectUtil.isImplementInterface(AT, LeetcodeJavaDebugEnhancer.class),
                "The class is not an AT class."
        );
        try {
            @SuppressWarnings("unchecked")
            // Create A LeetcodeJavaDebugEnhancer proxy instance by AT Class.
            LeetcodeJavaDebugEnhancer enhancer = EnhancerProxyFactory.createEnhancerProxy(
                    (Class<? extends LeetcodeJavaDebugEnhancer>) AT
            );
            LogUtil.setLogLevel(enhancer.getEnhancerLogLevel());
            LogUtil.logI("[Debugger4jBootstrap]Start leetcode-debugging at (AT) class: %s", AT.getSimpleName());
            PipelineProcessor.process(enhancer);
            LogUtil.logI("[Debugger4jBootstrap]Stop leetcode-debugging at (AT) class: %s", AT.getSimpleName());
        } catch (Throwable throwable) {
            LogUtil.logE("[Debugger4jBootstrap]Runtime error: %s: %s",
                    throwable.getClass().getName(),
                    throwable.getMessage()
            );
            throw new EnhancerException("Debugger4jBootstrap runtime error: " + throwable.getMessage(), throwable);
        }
    }
}
