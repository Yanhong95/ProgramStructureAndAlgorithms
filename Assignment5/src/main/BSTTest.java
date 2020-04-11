/*
 * Copyright (c) 2017. Phasmid Software
 */

package main;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ALL")
public class BSTTest {

    @Test
    public void testSetRoot1() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node node = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        System.out.println(bst);
    }

    @Test
    public void testSetRoot2() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node nodeX = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BSTSimple.Node nodeY = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BSTSimple.Node nodeZ = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        System.out.println(bst);
    }

    @Test
    public void testPut0() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        assertEquals(0, bst.size());
        bst.put("X", 42);
        assertEquals(1, bst.size());
    }

    @Test
    public void testPut1() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node node = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("Y", 99);
        BSTSimple.Node root = (BSTSimple.Node) tester.invokePrivate("getRoot");
        assertEquals("X",root.key);
        assertEquals("Y",root.larger.key);
        assertNull(root.smaller);
        assertEquals(2, bst.size());
    }

    @Test
    public void testPut2() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node node = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("X", 99);
        bst.put("Z", 37);
        BSTSimple.Node root = (BSTSimple.Node) tester.invokePrivate("getRoot");
        assertEquals("Y",root.key);
        assertEquals("X",root.smaller.key);
        assertEquals("Z",root.larger.key);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPut3() throws Exception {
        BSTdetail<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        bst.put("Y", 42);
        BSTSimple.Node root = (BSTSimple.Node) tester.invokePrivate("getRoot");
        assertEquals("Y", root.key);
        assertNull(root.smaller);
        assertNull(root.larger);
        bst.put("X", 99);
        assertEquals("X", root.smaller.key);
        bst.put("Z", 37);
        assertEquals("Z", root.larger.key);
        System.out.println(bst.toString());
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutN() throws Exception {
        BSTdetail<String, Integer> bst = new BSTSimple<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        System.out.println(bst);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutAll() throws Exception {
        final Map<String, Integer> map = new HashMap<>();
        map.put("Hello", 3);
        map.put("Goodbye", 5);
        map.put("Ciao", 6);
        BSTdetail<String, Integer> bst = new BSTSimple<>();
        bst.putAll(map);
        System.out.println(bst);
        assertEquals(map.size(), bst.size());
    }

    @Test
    public void testTraverse() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node node = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("X", 99);
        bst.put("Z", 37);
        Queue<String> queue = new Queue_Elements<String>();
        bst.inOrderTraverse((w, x) -> { queue.enqueue(w); return null; });
        assertEquals("X",queue.dequeue());
        assertEquals("Y",queue.dequeue());
        assertEquals("Z",queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testDelete1() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node node = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.delete("X");
        assertNull(bst.root);
        assertEquals(0, bst.size());
    }

    @Test
    public void testDelete2() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node node = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("Y",57);
        bst.put("Z",52);
        bst.put("C",51);
        bst.put("D",53);
        bst.put("G",53);
        bst.put("F",53);
        bst.put("P",53);
        bst.put("O",53);
        bst.put("B",53);
        bst.put("Q",53);
        System.out.println(bst);
        System.out.println("depth1 " + bst.depth());
        System.out.println("size1 " + bst.size());
        bst.delete("Q");
        System.out.println(bst);
        System.out.println("depth2 " + bst.depth());
        System.out.println("size2 " + bst.size());
        assertEquals(10, bst.size());
    }

    @Test
    public void testDelete3() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node node = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W",57);
        bst.delete("W");
        assertNull(bst.root.smaller);
        assertNull(bst.root.larger);
        assertEquals(1, bst.size());
    }

    @Test
    public void testDelete4() throws Exception {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTSimple.Node node = (BSTSimple.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W",57);
        bst.delete("A");
        assertEquals(2, bst.size());
    }

    @Test
    public void testSize1() {
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        for (int i=0; i<100; i++) bst.put(Integer.toString(i), i);
        assertEquals(100, bst.size());
    }

    @Test
    public void testSize2() {
        Random random = new Random(0L);
        BSTSimple<String, Integer> bst = new BSTSimple<>();
        for (int i=0; i<100; i++) bst.put(Integer.toString(random.nextInt(200)), i);
        assertEquals(79, bst.size());
    }
}