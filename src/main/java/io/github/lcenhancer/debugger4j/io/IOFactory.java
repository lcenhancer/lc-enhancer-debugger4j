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

package io.github.lcenhancer.debugger4j.io;

import io.github.lcenhancer.base.interfaces.InputProvider;
import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.interfaces.OutputConsumer;
import io.github.lcenhancer.base.utils.AssertUtil;
import io.github.lcenhancer.base.impl.io.ConsoleInputProvider;
import io.github.lcenhancer.base.impl.io.ConsoleOutputConsumer;

import java.util.Objects;

/**
 * <p>IOFactory is a factory class to product
 * the {@link InputProvider}
 * instance and the {@link OutputConsumer}
 * instance. </p>
 *
 * @author Jidcoo
 * @since 1.0.0
 */
public final class IOFactory {

    /**
     * Product a InputProvider instance by
     * {@link LeetcodeJavaDebugEnhancer} instance.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the InputProvider instance.
     */
    public static InputProvider getInputProvider(LeetcodeJavaDebugEnhancer enhancer) {
        AssertUtil.nonNull(enhancer, "The enhancer cannot be null.");
        if (Objects.nonNull(enhancer.getInputProvider())) {
            return enhancer.getInputProvider();
        }
        // By default, the console is used as the input provider.
        return new ConsoleInputProvider();
    }

    /**
     * Product a OutputConsumer instance by
     * {@link LeetcodeJavaDebugEnhancer} instance.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the OutputConsumer instance.
     */
    public static OutputConsumer getOutputConsumer(LeetcodeJavaDebugEnhancer enhancer) {
        AssertUtil.nonNull(enhancer, "The enhancer cannot be null.");
        if (Objects.nonNull(enhancer.getOutputConsumer())) {
            return enhancer.getOutputConsumer();
        }
        // By default, the console is used as the output consumer.
        return new ConsoleOutputConsumer();
    }
}
