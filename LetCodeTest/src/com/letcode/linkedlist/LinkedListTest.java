package com.letcode.linkedlist;

import com.sun.deploy.uitoolkit.ui.ConsoleWindow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LinkedListTest {
    public static void main(String[] args){
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(3);
        ListNode node5 = new ListNode(2);
        ListNode node6 = new ListNode(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;

        /* 测试链表中两两相邻节点交换 */
        //ListNode head = swapPairs(node1);

        /* 测试 获取两个量表相互交叉的节点 */
        //System.out.println("--------------------------------" +getIntersectionNode(node1,node1));

        isPalindrome_1(node1);
       //ListNode head =  reverseList_2(node1);
       //while (head != null){
       //    System.out.println("--------------------------------" +head.val);
       //    head = head.next;
       //}

    }

    /**
     * 对链表的两两相邻节点交换
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        ListNode temp = null;
        ListNode newNode = null;
        if(head != null || head.next != null){
            // 确定链表的头
            newNode = head.next;
            while(cur != null && cur.next != null){
                temp = cur.next.next;
                cur.next.next = cur;
               if(pre == null){
                   pre = cur;
                   cur.next = temp;
               }else{
                    pre.next = cur.next;
                    cur.next= temp;
                    pre = cur;
               }
                cur = cur.next;
            }
            return newNode;
        }else{
            return head;
        }
    }

    /**
     * 检查链表中是否有环  ----方法一龟兔赛跑法
     * @param head
     * @return
     */
    public static boolean hasCycle_1(ListNode head){
        if(head == null) return false;
        ListNode fast = null;
        ListNode slow = null;
        while(head !=null && head.next !=null && head.next.next != null){
            slow = head;
            fast = head.next.next;
            if(fast.val == slow.val){
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 检查链表中是否有环  ----方法二 HashSet
     * @param head
     * @return
     */
    public static boolean hasCycle_2(ListNode head){
        HashSet<ListNode> listNodes = new HashSet<>();
        while(head != null && head.next != null){
            if(listNodes.contains(head)){
                return true;
            }
            listNodes.add(head);
            head = head.next;
        }
        return  false;
    }

    /**
     * 查找两个链表的交叉节点
     * @param headA
     * @param headB
     * @return
     */
    public static  ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;
        int lenA = 0;
        int lenB = 0;
        ListNode nodeA = headA;
        ListNode nodeB = headB;

        while(nodeA !=null){
            lenA ++;
            nodeA = nodeA.next;
        }

        while (nodeB != null){
            lenB++;
            nodeB = nodeB.next;
        }

        int len = Math.abs((lenA - lenB));
        for (int i = 0 ;i < len; i++){
            if(lenA > lenB){
                headA = headA.next;
            }else{
                headB = headB.next;
            }
        }
        while(headA != null && headB != null){
            if(headA == headB) return headA;
            headA = headA.next;
            headB = headB.next;
        }
        return null;
    }


    /**
     * 删除有序链表中重复的节点
     * @param head
     * @return
     */
    public static   ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        ListNode newNode = head;
        while(newNode != null && newNode.next != null){
            if(newNode.val == newNode.next.val){
                newNode.next  = newNode.next.next;
            }else{
                newNode = newNode.next;
            }
        }
        return head;
    }

    /**
     * 删除链表中所有值为指定值的节点
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements(ListNode head, int val) {
        if(head == null )return  null;
        while(head != null){
            if(head.val == val) {
                head = head.next;
            }else{
                break;
            }
        }
        ListNode pre = head;
        while(head != null && head.next != null){
            if(head.next.val == val){
                head.next = head.next.next;
            }else{
                head = head.next;
            }
        }
        return pre;
    }

    /**
     * 反转指定的链表  ----时间复杂度太高 不推荐
     * @param head
     * @return
     */
    public static  ListNode reverseList(ListNode head) {
        if(head == null)return null;
        ListNode reverse = head;
        ListNode cur = head;
        while(head != null && head.next != null){
            if(head.next.next == null){
                reverse = head.next;
                head.next = null;
                head = cur;
                reverse.next = reverseList(head);
            }
            head = head.next;
        }
        return reverse;
    }

    /**
     * 反转链表 推荐方法
     * @param head
     * @return
     */
    public static  ListNode reverseList_2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p = head, q = head.next, tmp;
        p.next = null;
        while (q != null) {
            tmp = q.next;
            q.next = p;
            p = q;
            q = tmp;
        }
        return p;
    }

    /**
     * 判断一个链表是否为回文链表
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null) return true;
        ListNode p = head, q = head.next, temp;
        p.next = null;
        List<ListNode> list = new ArrayList<ListNode>();
        list.add(head);
        while( q != null){
            list.add(q);
            temp = q.next;
            q.next = p;
            p = q;
            q = temp;
        }
        int i = 0;
        while(p != null){
            if(list.get(i).val == p.val){
                p = p.next ;
                i++;
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个链表是否为回文链表  -----推荐方法
     * @param head
     * @return
     */
    public static boolean isPalindrome_1(ListNode head){
       /* 查找该链表的中间位置节点 */
        ListNode slow = head, fast = head, pre = slow;
       while(fast.next != null && fast.next.next != null){
           pre = slow;
           slow = slow.next;
           fast = fast.next.next;
       }
       /* 链表的后半段 */
       ListNode slownext = slow.next;

       /* 判断链表的长短为奇数还是偶数 */
       if(fast.next == null){
           pre.next = null;
       }else if(fast.next.next == null){
           slow.next = null;
       }
        /* 反转链表的后半段 */
        ListNode tail = reverseList_2(slownext);
        ListNode p=head,q=tail;
        while(p!=null&&q!=null){
            if(p.val!=q.val)
                return false;
            p=p.next;
            q=q.next;
        }
        return true;
    }

}

/**
 *  定义单链表
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

