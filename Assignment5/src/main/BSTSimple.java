package main;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BSTSimple<Key extends Comparable<Key>, Value> implements BSTdetail<Key, Value> {
    @Override
    public Boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * This implementation of putAll ensures that the keys are inserted into this BST in random order.
     * @param map a map of key value pairs
     */
    @Override
    public void putAll(Map<Key, Value> map) {
        @SuppressWarnings("unchecked") List<Key> ks = new ArrayList(map.keySet());
        Collections.shuffle(ks);
        for (Key k : ks) put(k, map.get(k));
    }

    @Override
    public int size() {
        return root != null ? root.count : 0;
    }

    @Override
    public void inOrderTraverse(BiFunction<Key, Value, Void> f) {
        doTraverse(0, root, f);
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    @Override
    public Value put(Key key, Value value) {
        NodeValue nodeValue = put(root, key, value);
        if (root == null) root = nodeValue.node;
        if (nodeValue.value == null) root.count++;
        return nodeValue.value;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    @Override
    public Set<Key> keySet() {
        return null;
    }

    public int depth(Key key) {
        return depth(getNode(root, key));
    }

    public BSTSimple() {
    }

    public BSTSimple(Map<Key, Value> map) {
        this();
        putAll(map);
    }

    Node root = null;

    private Value get(Node node, Key key) {
        Node result = getNode(node, key);
        return result!=null ? result.value : null;
    }

    private Node getNode(Node node, Key key) {
        if (node == null) return null;
        int cf = key.compareTo(node.key);
        if (cf < 0) return getNode(node.smaller, key);
        else if (cf > 0) return getNode(node.larger, key);
        else return node;
    }

    /**
     * Method to put the key/value pair into the subtree whose root is node.
     *
     * @param node  the root of a subtree
     * @param key   the key to insert
     * @param value the value to associate with the key
     * @return a tuple of Node and Value: Node is the
     */
    private NodeValue put(Node node, Key key, Value value) {
        // If node is null, then we return the newly constructed Node, and value=null
        if (node == null) {
            Node newNode = new Node(key, value, 0);
            return new NodeValue(newNode, value);
        }
        int cf = key.compareTo(node.key);
        if (cf == 0) {
            // If keys match, then we return the node and its value
            NodeValue result = new NodeValue(node, node.value);
            node.value = value;
            return result;
        } else if (cf < 0) {
            // if key is less than node's key, we recursively invoke put in the smaller subtree
            if (node.smaller == null) {
                Node newNode = new Node(key, value, 0);
                node.smaller = newNode;
                node.count++;
            } else{
                put(node.smaller, key, value);
            }
        } else {
            // if key is greater than node's key, we recursively invoke put in the larger subtree
            if (node.larger == null) {
                Node newNode = new Node(key, value, 0);
                node.larger = newNode;
                node.count++;
            } else{
                put(node.larger, key, value);
            }
        }
        node.count = size(node.smaller) + size(node.larger) + 1;
        return new NodeValue(node, node.value);
    }

    private Node delete(Node x, Key key) {
        // TO BE IMPLEMENTED ...
        /* Base Case: If the tree is empty */
        if (x == null) return null;
        /* Otherwise, recur down the tree */
        int comparator = key.compareTo(x.key);
        if (comparator < 0) {
            x.smaller = delete(x.smaller, key);
            x.count = size(x.smaller) + size(x.larger) + 1;
        }
        else if (comparator > 0) {
            x.larger = delete(x.larger, key);
            x.count = size(x.smaller) + size(x.larger) + 1;
        }
        // if key is same as root's key, then This is the node to be deleted
        else {
            // node with only one child or two children: Get the inorder successor (smallest in the right subtree)
            if (x.larger == null)
                return x.smaller;
            if (x.smaller == null)
                return x.larger;
            Node minNode = min(root.larger);
            x.value = minNode.value;
            x.larger = deleteMin(x.larger);
            x.count = size(x.smaller) + size(x.larger) + 1;
            return x;
        }
        return x;
        // ... END IMPLEMENTATION
    }

    private Node deleteMin(Node x) {
        if (x.smaller == null) return x.larger;
        x.smaller = deleteMin(x.smaller);
        x.count = 1 + size(x.smaller) + size(x.larger);
        return x;
    }

    private int size(Node x) {
        return x == null ? 0 : x.count;
    }

    private Node min(Node x) {
        if (x == null) throw new RuntimeException("min not implemented for null");
        while(x.smaller != null){
            x = x.smaller;
        }
        return x;
    }

    /**
     * Do a generic traverse of the binary tree starting with node
     * @param q determines when the function f is invoked ( lt 0: pre, ==0: in, gt 0: post)
     * @param node the node
     * @param f the function to be invoked
     */
    private void doTraverse(int q, Node node, BiFunction<Key, Value, Void> f) {
        if (node == null) return;
        if (q<0) f.apply(node.key, node.value);
        doTraverse(q, node.smaller, f);
        if (q==0) f.apply(node.key, node.value);
        doTraverse(q, node.larger, f);
        if (q>0) f.apply(node.key, node.value);
    }

    public int depth() {
        return depth(root);
    }

    private int depth(Node node) {
        if (node == null) return -1;
        else return 1 + Math.max(depth(node.smaller),depth(node.larger));
    }

    private class NodeValue {
        private final Node node;
        private final Value value;

        NodeValue(Node node, Value value) {
            this.node = node;
            this.value = value;
        }

        @Override
        public String toString() {
            return node + "<->" + value;
        }
    }

    class Node {
        Node(Key key, Value value, int depth) {
            this.key = key;
            this.value = value;
            this.depth = depth;
        }

        final Key key;
        Value value;
        final int depth;
        Node smaller = null;
        Node larger = null;
        int count = 1;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Node: " + key + ":" + value);
            if (smaller != null) sb.append(", smaller: " + smaller.key);
            if (larger != null) sb.append(", larger: " + larger.key);
            return sb.toString();
        }

    }

    private Node makeNode(Key key, Value value, int depth) {
        return new Node(key, value, depth);
    }

    private Node getRoot() {
        return root;
    }

    private void setRoot(Node node) {
        if(root==null){
            root = node;
            root.count++;
        }else
            root = node;
    }

    private void show(Node node, StringBuffer sb, int indent) {
        if (node == null) return;
        for (int i = 0; i < indent; i++) sb.append("  ");
        sb.append(node.key);
        sb.append(": ");
        sb.append(node.value);
        sb.append("\n");
        if (node.smaller != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("smaller: ");
            show(node.smaller, sb, indent + 1);
        }
        if (node.larger != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("larger: ");
            show(node.larger, sb, indent + 1);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        show(root, sb, 0);
        return sb.toString();
    }
    private static void generateBalancedBST( List<Integer> range, ArrayList<Integer> balancedBSTNodeList, int left, int right){
        if (left > right) return;
        int mid = left + (right - left)/2;
        balancedBSTNodeList.add(range.get(mid));
        if (right == left) return;
        generateBalancedBST(range,balancedBSTNodeList,left,mid-1);
        generateBalancedBST(range,balancedBSTNodeList,mid+1, right);
    }

    public static void main(String[] args){
        List<Integer> range = IntStream.rangeClosed(0, 1000).boxed().collect(Collectors.toList());

        for( int operationTimes = 1000; operationTimes <= 100000; operationTimes = operationTimes + 1000 ){
            int originalSize = 0,totalDepth = 0, totalSize = 0;
            for (int k = 0; k < 10; k++) {   // run experiment 10 times to get mean result;
                BSTSimple<Integer, Integer> balancedBST = new BSTSimple<>();
                ArrayList<Integer> balancedBSTNodeList = new ArrayList<>();
                generateBalancedBST(range, balancedBSTNodeList, 0, 1000-1);
                for (int j : balancedBSTNodeList) {
                    balancedBST.put(j, 1); // set value always 1
                }
                originalSize = balancedBST.size();
                for (int x = 0; x < operationTimes; x++) {
                    int operationRandom = (int)(1 + Math.random()*10);
                    int deletionRandom = (int) (1 + Math.random() * 1000 * 2);
                    if (operationRandom < 5 ) {
                        balancedBST.delete(deletionRandom);
                    } else {
                        balancedBST.put(deletionRandom, 1);
                    }
                }
                totalDepth += balancedBST.depth();
                totalSize += balancedBST.size();
            }
            System.out.println("Original BST size: " + originalSize +
                    " operation times: " + operationTimes +
                    " BST size after operation: " + (double)totalSize/10 +
                    " BST depth: " + (double)totalDepth/10 +
                    " sqrtSize: " + Math.sqrt((double)totalSize/10) +
                    " logSize: " + Math.log((double)totalSize/10));
        }
    }
}
