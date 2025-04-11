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

package io.github.lcenhancer.debugger4j.printer;

import io.github.lcenhancer.base.annotation.Require;
import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.strategy.BasePrintingStrategy;
import io.github.lcenhancer.base.utils.AssertUtil;
import io.github.lcenhancer.base.utils.BeanUtil;
import io.github.lcenhancer.base.utils.ContainerUtil;
import io.github.lcenhancer.base.utils.ReflectUtil;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>OutputPrinterFactory is a factory class
 * to product the {@link OutputPrinter} instance.</p>
 *
 * @author Jidcoo
 * @since 1.0.0
 */
public final class OutputPrinterFactory {

    /**
     * Built-in printing strategy set package location.
     */
    private static final String BUILT_IN_PRINTING_STRATEGY_PACKAGE = "io.github.lcenhancer.debugger4j.printer.builtin";

    /**
     * Product a OutputPrinter instance by
     * {@link LeetcodeJavaDebugEnhancer} instance.
     *
     * @param enhancer the LeetcodeJavaDebugEnhancer instance.
     * @return the OutputPrinter instance.
     */
    @SuppressWarnings(value = {"rawtypes"})
    public static OutputPrinter getOutputPrinter(LeetcodeJavaDebugEnhancer enhancer) {
        AssertUtil.nonNull(enhancer, "The enhancer cannot be null.");
        // Collect all builtin printing strategies.
        List<BasePrintingStrategy> builtinOutputPrintStrategies = BeanUtil.collectBeans(BasePrintingStrategy.class,
                        BUILT_IN_PRINTING_STRATEGY_PACKAGE,
                        (Class<BasePrintingStrategy> type) -> (
                                type.isAnnotationPresent(Require.class) &&
                                ReflectUtil.isExtendsClass(type, BasePrintingStrategy.class) &&
                                !Modifier.isAbstract(type.getModifiers())
                        ),
                        ReflectUtil::createInstance).stream().filter(Objects::nonNull).collect(Collectors.toList());
        // Add all enhancer's printStrategies to the list.
        List<BasePrintingStrategy<?>> outputPrintStrategies = enhancer.getOutputPrintStrategies();
        if (ContainerUtil.isNotEmpty(outputPrintStrategies)) {
            builtinOutputPrintStrategies.addAll(outputPrintStrategies);
        }
        return new OutputPrinter(builtinOutputPrintStrategies);
    }
}
