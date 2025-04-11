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

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link TreeNode}.
 *
 * @author Jidcoo
 */
public class TestSet5_TestCase07 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[3,2,1,6,0,5]", "[3,2,1]");
        expectString("[6,3,5,null,2,0,null,null,1]", "[3,null,2,null,1]");
    }

    class Solution {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            return construct(nums, 0, nums.length - 1);
        }

        public TreeNode construct(int[] nums, int left, int right) {
            if (left > right) {
                return null;
            }
            int best = left;
            for (int i = left + 1; i <= right; ++i) {
                if (nums[i] > nums[best]) {
                    best = i;
                }
            }
            TreeNode node = new TreeNode(nums[best]);
            node.left = construct(nums, left, best - 1);
            node.right = construct(nums, best + 1, right);
            return node;
        }
    }
}
