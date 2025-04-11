/*
 * Copyright (C) 2025-2030 LcEnhancer(https://github.com/lcenhancer).
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

package io.github.lcenhancer.debugger4j.executor;

import io.github.lcenhancer.base.interfaces.LeetcodeInvoker;
import io.github.lcenhancer.base.utils.AssertUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>LeetcodeInvokerFactory is a factory class
 * to product the {@link LeetcodeInvoker}
 * instance.</p>
 *
 * @author Jidcoo
 * @since 1.0.0
 */
public final class LeetcodeInvokerFactory {

    /**
     * LeetcodeInvoke id generator.
     */
    private static final AtomicInteger INVOKER_ID_GENERATOR = new AtomicInteger(1);

    /**
     * Product a LeetcodeInvoker instance by
     * {@link Method} instance.
     *
     * @param method the method instance.
     * @return the LeetcodeInvoker instance.
     */
    public static LeetcodeInvoker getLeetcodeInvoker(Method method) {
        return getLeetcodeInvoker(method, INVOKER_ID_GENERATOR.getAndIncrement());
    }

    /**
     * Product a LeetcodeInvoker instance by
     * {@link Method} instance and custom invoker order.
     *
     * @param method the method instance.
     * @param order  the invoker order.
     * @return the LeetcodeInvoker instance.
     */
    public static LeetcodeInvoker getLeetcodeInvoker(Method method, Integer order) {
        AssertUtil.nonNull(method, "The method cannot be null.");
        AssertUtil.nonNull(order, "The order cannot be null.");
        return new MethodLeetcodeInvoker(method, INVOKER_ID_GENERATOR.getAndIncrement(), order);
    }

    /**
     * Product a LeetcodeInvoker instance by
     * {@link Constructor} instance.
     *
     * @param constructor the constructor instance.
     * @return the LeetcodeInvoker instance.
     */
    public static LeetcodeInvoker getLeetcodeInvoker(Constructor<?> constructor) {
        return getLeetcodeInvoker(constructor, INVOKER_ID_GENERATOR.getAndIncrement());
    }

    /**
     * Product a LeetcodeInvoker instance by
     * {@link Constructor} instance and custom invoker order.
     *
     * @param constructor the constructor instance.
     * @param order       the invoker order.
     * @return the LeetcodeInvoker instance.
     */
    public static LeetcodeInvoker getLeetcodeInvoker(Constructor<?> constructor, Integer order) {
        AssertUtil.nonNull(constructor, "The constructor cannot be null.");
        AssertUtil.nonNull(order, "The order cannot be null.");
        return new ConstructorLeetcodeInvoker(constructor, INVOKER_ID_GENERATOR.getAndIncrement(), order);
    }
}
