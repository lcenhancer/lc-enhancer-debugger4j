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
import io.github.lcenhancer.base.interfaces.OutputConsumer;
import io.github.lcenhancer.base.utils.AssertUtil;
import io.github.lcenhancer.debugger4j.executor.LeetcodeExecutorFactory;
import io.github.lcenhancer.debugger4j.io.IOFactory;
import io.github.lcenhancer.debugger4j.parser.InputParserFactory;
import io.github.lcenhancer.debugger4j.printer.OutputPrinterFactory;

/**
 * <p>PipelineProcessor is a publicly
 * available BootstrapPipeline processor.
 * It has used a proxy to access {@link BootstrapPipeline}.
 * </p>
 *
 * <p>The {@link #process(LeetcodeJavaDebugEnhancer)} method
 * of PipelineProcessor is to proxy external callers to
 * execute {@link BootstrapPipeline}.
 * </p>
 *
 * @author Jidcoo
 * @since 1.0.0
 */
public final class PipelineProcessor {

    /**
     * Do starting real enhancement pipeline with
     * LeetcodeJavaDebugEnhancer instance.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     */
    public static void process(LeetcodeJavaDebugEnhancer enhancer) throws Exception, Error {
        // Check NPE.
        AssertUtil.nonNull(enhancer, "The enhancer cannot be null.");

        Class<?> payload = enhancer.getEnhancerPayload();
        AssertUtil.nonNull(payload, "The enhancer payload cannot be null.");

        // Create a LeetcodeExecutor from the enhancer.
        Object leetcodeExecutor = LeetcodeExecutorFactory.getLeetcodeExecutor(payload);
        // Create an OutputPrinter from the enhancer.
        Object outputPrinter = OutputPrinterFactory.getOutputPrinter(enhancer);
        // Create a InputParser from the enhancer.
        Object inputParser = InputParserFactory.getInputParser(enhancer);

        try (
                // Get or create a InputProvider from the enhancer.
                InputProvider inputProvider = IOFactory.getInputProvider(enhancer);
                // Get or create OutputConsumer from the enhancer.
                OutputConsumer outputConsumer = IOFactory.getOutputConsumer(enhancer)

        ) {
            // Create a pipeline instance by enhancer's components.
            new BootstrapPipeline(
                    enhancer,
                    inputProvider,
                    outputConsumer,
                    outputPrinter,
                    leetcodeExecutor,
                    inputParser
            ).run();
        }

    }
}