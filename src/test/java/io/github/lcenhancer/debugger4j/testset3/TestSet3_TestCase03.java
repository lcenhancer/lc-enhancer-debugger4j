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

package io.github.lcenhancer.debugger4j.testset3;

import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.struct.ListNode;
import io.github.lcenhancer.debugger4j.UnitTestDriver;
import org.junit.Before;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link ListNode}.
 *
 * @author Jidcoo
 */
public class TestSet3_TestCase03 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[0,1,2,3] [0,1,3]", "[0,1,2,3,4] [0,3,1,4]");
        expectString("2", "2");
    }

    class Solution {
        public int numComponents(ListNode head, int[] nums) {
            Set<Integer> numsSet = new HashSet<Integer>();
            for (int num : nums) {
                numsSet.add(num);
            }
            boolean inSet = false;
            int res = 0;
            while (head != null) {
                if (numsSet.contains(head.val)) {
                    if (!inSet) {
                        inSet = true;
                        res++;
                    }
                } else {
                    inSet = false;
                }
                head = head.next;
            }
            return res;
        }
    }
}
