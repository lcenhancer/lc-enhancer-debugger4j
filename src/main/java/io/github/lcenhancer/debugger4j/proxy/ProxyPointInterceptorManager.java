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

import io.github.lcenhancer.base.annotation.Require;
import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.interfaces.ProxyPointInterceptor;
import io.github.lcenhancer.base.proxy.ProxyPointParameterView;
import io.github.lcenhancer.base.utils.*;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>ProxyPointInterceptorManager is a proxy point interceptor
 * manager.
 *
 * <p>It will scan all possible {@link ProxyPointInterceptor}
 * for the {@link LeetcodeJavaDebugEnhancer} proxy point surround
 * interception processing.
 *
 * @author Jidcoo
 * @since 1.0.0
 */
@SuppressWarnings(value = {"rawtypes", "unchecked"})
final class ProxyPointInterceptorManager {

    /**
     * ProxyPointInterceptor scanner base package location.
     */
    private static final String PROXY_POINT_INTERCEPTOR_SCANNER_BASE_PACKAGE = "io.github.lcenhancer.debugger4j.proxy.bi";

    /**
     * ProxyPointInterceptor instances map(interceptPoint->List<ProxyPointInterceptor>).
     */
    private final Map<String, List<ProxyPointInterceptor>> proxyPointInterceptorsMap;

    /**
     * Create a ProxyPointInterceptorManager instance.
     */
    ProxyPointInterceptorManager() {
        // init all proxy point interceptors.
        proxyPointInterceptorsMap = BeanUtil.collectBeans(ProxyPointInterceptor.class, PROXY_POINT_INTERCEPTOR_SCANNER_BASE_PACKAGE,
                (Class<? extends ProxyPointInterceptor> type) -> {
                    boolean baseValidResult = type.isAnnotationPresent(Require.class)
                            && ReflectUtil.isImplementInterface(type, ProxyPointInterceptor.class)
                            && !Modifier.isAbstract(type.getModifiers());
                    if (!baseValidResult) {
                        return false;
                    }
                    Require requireAnnotation = (Require) type.getAnnotation(Require.class);
                    return requireAnnotation.types().length > 0 && ProxyPointInterceptor.class.equals(requireAnnotation.types()[0]);
                },
                ReflectUtil::createInstance
        ).stream().filter(Objects::nonNull).collect(
                Collectors.groupingBy(ProxyPointInterceptor::interceptPoint,
                Collectors.collectingAndThen(Collectors.toList(),
                list -> {
                    OrderUtil.descSort(list);
                    return list;
                }
        )));
    }

    /**
     * Intercept and process parameters before
     * invoking proxy point.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @param pointName                 the proxy point name.
     * @param parameterView             the proxy point parameter view.
     */
    public void doInterceptOnBefore(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer,
                                    String pointName, ProxyPointParameterView parameterView) {
        AssertUtil.notBlank(pointName, "The proxy point name cannot be blank.");
        AssertUtil.nonNull(parameterView, "The proxy point parameter view cannot be null.");
        List<ProxyPointInterceptor> interceptors = proxyPointInterceptorsMap.getOrDefault(pointName, null);
        if (ContainerUtil.isEmpty(interceptors)) {
            return;
        }
        for (ProxyPointInterceptor interceptor : interceptors) {
            interceptor.onBefore(leetcodeJavaDebugEnhancer, parameterView);
        }
    }

    /**
     * Intercept and process proxy point result
     * after invoking proxy point.
     *
     * @param leetcodeJavaDebugEnhancer the LeetcodeJavaDebugEnhancer instance.
     * @param pointName                 the proxy point name.
     * @param o                         the proxy point result.
     * @return the processed proxy point result.
     */
    public Object doInterceptOnAfter(LeetcodeJavaDebugEnhancer leetcodeJavaDebugEnhancer, String pointName, Object o) {
        AssertUtil.notBlank(pointName, "The proxy point name cannot be blank.");
        List<ProxyPointInterceptor> interceptors = proxyPointInterceptorsMap.getOrDefault(pointName, null);
        if (ContainerUtil.isNotEmpty(interceptors)) {
            for (ProxyPointInterceptor interceptor : interceptors) {
                o = interceptor.onAfter(leetcodeJavaDebugEnhancer, o);
            }
        }
        return o;
    }
}
