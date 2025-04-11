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

package io.github.lcenhancer.debugger4j.testset5;

import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.struct.TreeNode;
import io.github.lcenhancer.debugger4j.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link TreeNode}.
 *
 * @author Jidcoo
 */
public class TestSet5_TestCase05 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[2,3,5,8,13,21,34]", "[0,1,2,0,0,0,0,1,1,1,1,2,2,2,2]");
        expectString("[2,5,3,8,13,21,34]", "[0,2,1,0,0,0,0,2,2,2,2,1,1,1,1]");
    }

    class Solution {
        public TreeNode reverseOddLevels(TreeNode root) {
            Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
            queue.offer(root);
            boolean isOdd = false;
            while (!queue.isEmpty()) {
                int sz = queue.size();
                List<TreeNode> arr = new ArrayList<TreeNode>();
                for (int i = 0; i < sz; i++) {
                    TreeNode node = queue.poll();
                    if (isOdd) {
                        arr.add(node);
                    }
                    if (node.left != null) {
                        queue.offer(node.left);
                        queue.offer(node.right);
                    }
                }
                if (isOdd) {
                    for (int l = 0, r = sz - 1; l < r; l++, r--) {
                        int temp = arr.get(l).val;
                        arr.get(l).val = arr.get(r).val;
                        arr.get(r).val = temp;
                    }
                }
                isOdd ^= true;
            }
            return root;
        }
    }
}
