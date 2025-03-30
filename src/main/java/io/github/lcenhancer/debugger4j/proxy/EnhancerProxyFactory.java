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

package io.github.lcenhancer.debugger4j.proxy;

import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.utils.AssertUtil;
import io.github.lcenhancer.base.utils.ReflectUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * <p>EnhancerProxyFactory is a factory class
 * to product the {@link LeetcodeJavaDebugEnhancer} instance proxy.</p>
 *
 * @author Jidcoo
 * @since 1.0.0
 */
public class EnhancerProxyFactory {

    /**
     * Create A LeetcodeJavaDebugEnhancer instance proxy by AT Class.
     *
     * @param AT the AT class.
     * @return LeetcodeJavaDebugEnhancer instance proxy.
     */
    public static LeetcodeJavaDebugEnhancer createEnhancerProxy(Class<? extends LeetcodeJavaDebugEnhancer> AT) {
        AssertUtil.nonNull(AT, "The AT class cannot be null.");
        return (LeetcodeJavaDebugEnhancer) Proxy.newProxyInstance(
                AT.getClassLoader(),
                new Class[]{LeetcodeJavaDebugEnhancer.class},
                new EnhancerProxyHandler(ReflectUtil.createInstance(AT))
        );
    }

    /**
     * Aware source LeetcodeJavaDebugEnhancer from the specific LeetcodeJavaDebugEnhancer instance.
     *
     * @param enhancer the specific LeetcodeJavaDebugEnhancer instance.
     * @return the source LeetcodeJavaDebugEnhancer instance.
     */
    public static LeetcodeJavaDebugEnhancer awareSourceEnhancer(LeetcodeJavaDebugEnhancer enhancer) {
        if (Objects.isNull(enhancer)) {
            return null;
        }
        if (!Proxy.isProxyClass(enhancer.getClass())) {
            return enhancer;
        }
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(enhancer);
        if (invocationHandler instanceof EnhancerProxyHandler) {
            return awareSourceEnhancer(((EnhancerProxyHandler) invocationHandler).getTarget());
        }
        return enhancer;
    }
}
