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

package io.github.lcenhancer.debugger4j.testset3;

import io.github.lcenhancer.base.interfaces.LeetcodeJavaDebugEnhancer;
import io.github.lcenhancer.base.struct.TreeNode;
import io.github.lcenhancer.base.struct.ListNode;
import io.github.lcenhancer.debugger4j.UnitTestDriver;
import org.junit.Before;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Tests for basic function of {@link LeetcodeJavaDebugEnhancer}
 * with data structure {@link ListNode} and {@link TreeNode}.
 *
 * @author Jidcoo
 */
public class TestSet3_TestCase07 extends UnitTestDriver {

    @Before
    public void onBefore() {
        registerDriverWithAutoCustomStdIn("[1,2,3,4,5,null,7,8]");
        ignoreTestResult();
    }

    class Solution {
        public ListNode[] listOfDepth(TreeNode tree) {
            Queue<TreeNode> queue = new LinkedList<>();
            List<List<Integer>> lists = new ArrayList<>();
            if (tree != null) {
                queue.add(tree);
            }
            while (!queue.isEmpty()) {
                List<Integer> list = new ArrayList<>();
                for (int i = queue.size(); i > 0; i--) {
                    TreeNode tmp = queue.poll();
                    list.add(tmp.val);
                    if (tmp.left != null) {
                        queue.add(tmp.left);
                    }
                    if (tmp.right != null) {
                        queue.add(tmp.right);
                    }
                }
                lists.add(list);
            }
            ListNode[] a = new ListNode[lists.size()];

            for (int i = 0; i < lists.size(); i++) {
                ListNode l = new ListNode(0);
                ListNode ll = l;
                for (int j = 0; j < lists.get(i).size(); j++) {
                    ListNode tmp = new ListNode(lists.get(i).get(j));
                    l.next = tmp;
                    l = l.next;
                }
                a[i] = ll.next;
            }
            return a;
        }
    }
}
