package leetcode;

import sun.security.util.Length;

import java.nio.charset.Charset;

/**
 * Created by Niki on 2020/7/15 17:26
 */
public class AddTwoNumbers {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        String l1Str = list2Str(l1);
        String l2Str = list2Str(l2);
        final Integer l1i = Integer.valueOf(l1Str);
        final Integer l2i = Integer.valueOf(l2Str);
        int l3 = l1i + l2i;
        String l3Str = String.valueOf(l3);
        final char[] chars = l3Str.toCharArray();

        ListNode first = new ListNode(chars[0], null);
        ListNode tmp = first;
        for (int i = 1; i < chars.length; i++) {
            ListNode listNode = new ListNode(chars[i], null);
            tmp.next = listNode;
            tmp = listNode;

        }
        return first;
    }

    private String list2Str(ListNode listNode) {
        String l1Str = listNode.val+"";
        while (true) {
            final ListNode next = listNode.next;
            l1Str += next.val;
            if (next == null) {
                break;
            }
        }
        return l1Str;
    }

    private ListNode toListNode(String nums) {
        final char[] chars = nums.toCharArray();
        final int length = chars.length;
        ListNode first = new ListNode(Integer.valueOf(String.valueOf(chars[length-1])), null);
        ListNode tmp = first;
        for (int i = length-2; i >= 0; i--) {
            final Integer integer = Integer.valueOf(String.valueOf(chars[i]));
            ListNode listNode = new ListNode(integer, null);
            tmp.next = listNode;
            tmp = listNode;

        }
        return first;
    }

    public  ListNode addTwoNumber(ListNode l1, ListNode l2) {
        ListNode dummyHeader = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHeader;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);

        }
        return dummyHeader.next;
    }

    public static void main(String[] args) {
        ListNode first = new AddTwoNumbers().toListNode("342");
        ListNode temp = first;
        System.out.println(temp.val);
        while (true) {
            if (temp.next == null) {
                return;
            }
            temp = temp.next;
            System.out.println(temp.val);
        }
    }

}
