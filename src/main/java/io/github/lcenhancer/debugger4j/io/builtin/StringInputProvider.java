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

package io.github.lcenhancer.debugger4j.io.builtin;


import io.github.lcenhancer.base.interfaces.InputProvider;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>StringInputProvider is a {@link InputProvider} and
 * extends on {@link FileInputProvider}.</p>
 *
 * <p>StringInputProvider use the {@link String}
 * as input source.
 * </p>
 *
 * @author Jidcoo
 * @see String
 * @since 1.0.0
 */
public class StringInputProvider extends FileInputProvider {

    /**
     * Create a StringInputProvider by string.
     *
     * @param string  the input string.
     * @param charset the charset.
     */
    public StringInputProvider(String string, Charset charset) {
        super(new ByteArrayInputStream(string.getBytes(charset)));
    }

    /**
     * Create a StringInputProvider by string.
     *
     * @param string the input string.
     */
    public StringInputProvider(String string) {
        this(string, StandardCharsets.UTF_8);
    }
}
