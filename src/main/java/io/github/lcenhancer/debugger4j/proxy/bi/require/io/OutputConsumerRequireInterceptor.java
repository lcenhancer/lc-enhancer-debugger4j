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

package io.github.lcenhancer.debugger4j.proxy.bi.require.io;

import io.github.lcenhancer.base.annotation.Require;
import io.github.lcenhancer.base.exception.EnhancerException;
import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.interfaces.OutputConsumer;
import io.github.lcenhancer.base.interfaces.ProxyPointInterceptor;
import io.github.lcenhancer.base.utils.AssertUtil;
import io.github.lcenhancer.base.utils.ContainerUtil;
import io.github.lcenhancer.debugger4j.io.builtin.ConsoleOutputConsumer;
import io.github.lcenhancer.debugger4j.io.builtin.FileOutputConsumer;
import io.github.lcenhancer.debugger4j.io.builtin.MultipleOutputConsumer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * <p>OutputConsumerRequireInterceptor is a proxy-point
 * interceptor used to support annotating config output
 * consumer using {@link Require} annotation.
 *
 * @author Jidcoo
 * @see OutputConsumer
 * @see AbsIORequireSupporter
 * @see ProxyPointInterceptor
 * @since 1.0.0
 */
@Require(types = ProxyPointInterceptor.class)
final class OutputConsumerRequireInterceptor extends AbsIORequireSupporter<OutputConsumer>
        implements ProxyPointInterceptor<OutputConsumer> {

    /**
     * Create an OutputConsumerRequireInterceptor instance.
     */
    @SuppressWarnings("all")
    OutputConsumerRequireInterceptor() {
        enableIOSourceType(ConsoleOutputConsumer.class, (source) -> new ConsoleOutputConsumer());
        enableIOSourceType(FileOutputConsumer.class, (source) -> {
            AssertUtil.notBlank(
                    source,
                    "The file path string value cannot be blank in FileOutputConsumer Require type."
            );
            try {
                return new FileOutputConsumer(source);
            } catch (IOException e) {
                throw new EnhancerException("The file cannot found in FileOutputConsumer Require: " + source, e);
            }
        });
    }

    /**
     * Get the order of the object.
     *
     * @return the int order of the object.
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Return the proxy point name that needs to
     * be intercepted.
     *
     * @return the proxy point name.
     */
    @Override
    public String interceptPoint() {
        return "getOutputConsumer";
    }

    /**
     * Intercept and process proxy point result
     * after invoking proxy point.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @param outputConsumer            the proxy point result.
     * @return the processed proxy point result.
     */
    @Override
    @SuppressWarnings("all")
    public OutputConsumer onAfter(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer, OutputConsumer outputConsumer) {
        List<OutputConsumer> outputConsumerList = awareIOSources(leetcodeJavaDebugEnhancer);
        if (ContainerUtil.isNotEmpty(outputConsumerList)) {
            if (Objects.nonNull(outputConsumer)) {
                outputConsumerList.add(outputConsumer);
            }
            outputConsumer = new MultipleOutputConsumer(outputConsumerList);
        }
        return outputConsumer;
    }
}
