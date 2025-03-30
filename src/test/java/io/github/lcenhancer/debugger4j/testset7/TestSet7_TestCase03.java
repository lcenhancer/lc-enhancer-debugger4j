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

package io.github.lcenhancer.debugger4j.testset7;

import io.github.lcenhancer.base.struct.TreeNode;
import io.github.lcenhancer.debugger4j.UnitTestDriver;
import org.junit.Before;

import java.util.List;

/**
 * Tests for {@link io.github.lcenhancer.debugger4j.parser.builtin.BasicListParameterAcceptStrategy}
 *
 * @author Jidcoo
 */
public class TestSet7_TestCase03 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[[3.0,null,2],[3.0,null,2]]");
        expectString("2");
    }

    class Solution {
        public int tree(List<TreeNode> node) {
            return node.size();
        }
    }
}
