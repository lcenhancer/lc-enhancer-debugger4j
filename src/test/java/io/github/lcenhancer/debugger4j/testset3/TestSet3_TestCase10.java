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

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link ListNode}.
 *
 * @author Jidcoo
 */
public class TestSet3_TestCase10 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,2,3,4,5] 2");
        expectString("[4,5,1,2,3]");
    }

    class Solution {
        public ListNode rotateRight(ListNode head, int k) {
            if (k == 0 || head == null || head.next == null) {
                return head;
            }
            int n = 1;
            ListNode iter = head;
            while (iter.next != null) {
                iter = iter.next;
                n++;
            }
            int add = n - k % n;
            if (add == n) {
                return head;
            }
            iter.next = head;
            while (add-- > 0) {
                iter = iter.next;
            }
            ListNode ret = iter.next;
            iter.next = null;
            return ret;
        }
    }
}
