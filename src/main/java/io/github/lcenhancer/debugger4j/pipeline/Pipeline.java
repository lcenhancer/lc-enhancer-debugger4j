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

package io.github.lcenhancer.debugger4j.pipeline;

import io.github.lcenhancer.base.interfaces.InputProvider;
import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.interfaces.Order;
import io.github.lcenhancer.base.interfaces.OutputConsumer;

/**
 * <p>Pipeline provides important components
 * in the enhancer runtime pipeline.</p>
 *
 * <p>Note: Pipeline implements
 * the {@link Order}, please do not forget to
 * implement the {@link #getOrder()} method.
 * Because it will directly reflect the priority of
 * this pipeline object!!!
 * </p>
 *
 * @author Jidcoo
 * @since 1.0.0
 */
abstract class Pipeline implements Order {

    /**
     * The base pipeline.
     */
    private Pipeline delegate;

    /**
     * Get enhancer of this pipeline.
     *
     * @return enhancer.
     */
    protected LeetcodeJavaDebugEnhancer getEnhancer() {
        return delegate.getEnhancer();
    }

    /**
     * Get input provider of this pipeline.
     *
     * @return input provider.
     */
    protected InputProvider getInputProvider() {
        return delegate.getInputProvider();
    }

    /**
     * Get output consumer of this pipeline.
     *
     * @return output consumer.
     */
    protected OutputConsumer getOutputConsumer() {
        return delegate.getOutputConsumer();
    }

    /**
     * Get output printer of this pipeline.
     *
     * @return output printer instance.
     */
    protected Object getOutputPrinter() {
        return delegate.getOutputPrinter();
    }

    /**
     * Get leetcode executor of this pipeline.
     *
     * @return leetcode executor instance.
     */
    protected Object getLeetcodeExecutor() {
        return delegate.getLeetcodeExecutor();
    }

    /**
     * Get input parser of this pipeline.
     *
     * @return input parser instance.
     */
    protected Object getInputParser() {
        return delegate.getInputParser();
    }
}
