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

import io.github.lcenhancer.base.annotation.Require;
import io.github.lcenhancer.base.interfaces.LeetcodeInvoker;
import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.utils.AssertUtil;
import io.github.lcenhancer.base.utils.ReflectUtil;
import io.github.lcenhancer.debugger4j.executor.LeetcodeExecutorFactory;
import io.github.lcenhancer.debugger4j.executor.LeetcodeExecutorProcessor;
import io.github.lcenhancer.debugger4j.executor.LeetcodeInvokerFactory;
import io.github.lcenhancer.debugger4j.parser.InputParserProcessor;
import io.github.lcenhancer.debugger4j.proxy.EnhancerProxyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DataStructureDesignScenePipeline is a
 * {@link Pipeline} used to support
 * leetcode data structure design questions.
 *
 * @author Jidcoo
 * @since 1.0.0
 */
@Require
final class DataStructureDesignScenePipeline extends Pipeline {

    /**
     * Process on data structure design scene question.
     *
     * @param operations operation list.
     * @param data       data list.
     * @return question result.
     */
    @Require
    List<Object> run(List<String> operations, List<List<Object>> data) {
        AssertUtil.notEmpty(operations, "The operation list cannot be empty.");
        AssertUtil.notEmpty(data, "The data list cannot be empty.");
        AssertUtil.isTrue(operations.size() == data.size(), "The size of lists operation and data is not equal.");
        // Aware inner-class from leetcode executor.
        Class<?> dataStructureKlass = (Class<?>) ReflectUtil.getFieldValue("instance", Object.class, getLeetcodeExecutor());
        AssertUtil.nonNull(dataStructureKlass, "The data structure class instance cannot be null.");
        // Collect all constructors and method from dataStructureKlass.
        List<LeetcodeInvoker> constructorInvokers = Arrays.stream(dataStructureKlass.getDeclaredConstructors())
                .map(constructor -> LeetcodeInvokerFactory.getLeetcodeInvoker(constructor, Integer.MAX_VALUE))
                // Enable Friendly-Matching-Mode to ConstructorLeetcodeInvoker instance.
                .peek(leetcodeInvoker -> ReflectUtil.setFieldValue("matchingFriendly", boolean.class, Boolean.TRUE, leetcodeInvoker))
                .collect(Collectors.toList());
        List<LeetcodeInvoker> methodInvokers = Arrays.stream(dataStructureKlass.getDeclaredMethods())
                .map(method -> LeetcodeInvokerFactory.getLeetcodeInvoker(method, Integer.MAX_VALUE))
                .collect(Collectors.toList());
        // Map invokerName -> List<LeetcodeInvoker>
        Map<String, List<LeetcodeInvoker>> invokersMap = Stream
                .concat(constructorInvokers.stream(), methodInvokers.stream())
                .collect(Collectors.groupingBy(LeetcodeInvoker::getInvokerName));

        List<Object> invokerReturnCollector = new ArrayList<>();
        Object dataStructureInstance = null;
        // Run operations stream.
        for (int idx = 0; idx < operations.size(); idx++) {
            String operation = operations.get(idx);
            List<Object> input = data.get(idx);
            // Get candidate leetcode invokers by cur operation.
            List<LeetcodeInvoker> leetcodeInvokers = invokersMap.get(operation);
            AssertUtil.notEmpty(leetcodeInvokers, "Cannot find any candidate leetcode invoker by operation: " + operation);
            // Create a LeetcodeExecutor instance.
            Object leetcodeExecutor = LeetcodeExecutorFactory.getLeetcodeExecutor(idx == 0 ? getEnhancer()
                            : dataStructureInstance, leetcodeInvokers.toArray(new LeetcodeInvoker[0]));
            // Parse input.
            Object inputObject = InputParserProcessor.process(getInputParser(), leetcodeExecutor, input);
            // Process leetcode exec.
            Object outputObject = LeetcodeExecutorProcessor.process(leetcodeExecutor, inputObject);
            // Collect leetcode exec return object.
            if (idx == 0) {
                dataStructureInstance = outputObject;
                // Ignore the data structure instance return base on Leetcode output style.
                invokerReturnCollector.add(null);
            } else {
                invokerReturnCollector.add(outputObject);
            }
        }

        return invokerReturnCollector;
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
     * Get enhancer of this pipeline.
     *
     * @return enhancer.
     */
    @Override
    protected LeetcodeJavaDebugEnhancer getEnhancer() {
        // Get the source instance.
        return EnhancerProxyFactory.awareSourceEnhancer(super.getEnhancer());
    }
}