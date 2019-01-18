import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;

import static org.junit.Assert.*;

public class AVLTreeTest {

    @Test()
    public void balanceLeftTest() {

        AVLTree<Integer> avlTree = new AVLTree<>();

        // Check first left rotation is fine.
        avlTree.insert(100);
        avlTree.insert(200);
        avlTree.insert(300);

        Object[] avlArray1 = avlTree.asArray();
        assertEquals(200, avlArray1[0]);
        assertEquals(100, avlArray1[1]);
        assertEquals(300, avlArray1[2]);


        // Check second left rotation is fine.
        avlTree.insert(400);
        avlTree.insert(500);

        Object[] avlArray2 = avlTree.asArray();
        assertEquals(200, avlArray2[0]);
        assertEquals( 100, avlArray2[1]);
        assertEquals( 400, avlArray2[2]);
        assertNull(avlArray2[3]);
        assertNull(avlArray2[4]);
        assertEquals(300, avlArray2[5]);
        assertEquals(500, avlArray2[6]);
    }


    @Test()
    public void balanceRightTest() {

        AVLTree<Integer> avlTree = new AVLTree<>();

        // Check first left rotation is fine.
        avlTree.insert(100);
        avlTree.insert(90);
        avlTree.insert(80);

        Object[] avlArray1 = avlTree.asArray();
        assertEquals(90, avlArray1[0]);
        assertEquals(80, avlArray1[1]);
        assertEquals(100, avlArray1[2]);


        // Check second left rotation is fine.
        avlTree.insert(50);
        avlTree.insert(40);

        Object[] avlArray2 = avlTree.asArray();
        assertEquals(90, avlArray2[0]);
        assertEquals(50, avlArray2[1]);
        assertEquals(100, avlArray2[2]);
        assertEquals(40, avlArray2[3]);
        assertEquals(80, avlArray2[4]);
        assertNull(avlArray2[5]);
        assertNull(avlArray2[6]);
    }


    @Test()
    public void balanceLeftRightTest() {

        AVLTree<Integer> avlTree = new AVLTree<>();

        avlTree.insert(100);
        avlTree.insert(50);
        avlTree.insert(70);

        Object[] avlArray1 = avlTree.asArray();
        assertEquals(70, avlArray1[0]);
        assertEquals(50, avlArray1[1]);
        assertEquals(100, avlArray1[2]);

        // Check second left rotation is fine.
        avlTree.insert(30);
        avlTree.insert(40);

        Object[] avlArray2 = avlTree.asArray();
        assertEquals(70, avlArray2[0]);
        assertEquals(40, avlArray2[1]);
        assertEquals(100, avlArray2[2]);
        assertEquals(30, avlArray2[3]);
        assertEquals(50, avlArray2[4]);
        assertNull(avlArray2[5]);
        assertNull(avlArray2[6]);

    }

    @Test()
    public void balanceRightLeftTest() {

        AVLTree<Integer> avlTree = new AVLTree<>();

        avlTree.insert(100);
        avlTree.insert(200);
        avlTree.insert(150);

        Object[] avlArray1 = avlTree.asArray();
        assertEquals(150, avlArray1[0]);
        assertEquals(100, avlArray1[1]);
        assertEquals(200, avlArray1[2]);

        // Check second left rotation is fine.

        avlTree.insert(300);
        avlTree.insert(250);

        Object[] avlArray2 = avlTree.asArray();
        assertEquals(150, avlArray2[0]);
        assertEquals(100, avlArray2[1]);
        assertEquals(250, avlArray2[2]);
        assertNull(avlArray2[3]);
        assertNull(avlArray2[4]);
        assertEquals(200, avlArray2[5]);
        assertEquals(300, avlArray2[6]);

    }

    @Test()
    public void balanceTest() {

        AVLTree<Integer> avlTree = new AVLTree<>();

        // Left rotation.
        avlTree.insert(100);
        avlTree.insert(200);
        avlTree.insert(300);

        // Right rotation.
        avlTree.insert(50);
        avlTree.insert(25);

        // Left-Right rotation.
        avlTree.insert(75);

        // Right-Left rotation.
        avlTree.insert(250);

        Object[] avlArray1 = avlTree.asArray();
        assertEquals(100, avlArray1[0]);
        assertEquals(50, avlArray1[1]);
        assertEquals(250, avlArray1[2]);
        assertEquals(25, avlArray1[3]);
        assertEquals(75, avlArray1[4]);
        assertEquals(200, avlArray1[5]);
        assertEquals(300, avlArray1[6]);


    }

    @Test()
    public void balanceTest2() {

        // single right

        AVLTree<Integer> avlTree = new AVLTree<>();

        // Single right rotation.
        avlTree.insert(100);
        avlTree.insert(50);
        avlTree.insert(25);
        avlTree.insert(40);
        avlTree.insert(30);
        avlTree.insert(75);
        avlTree.insert(90);
        avlTree.insert(60);
        avlTree.insert(55);
        avlTree.insert(45);
        avlTree.insert(41);
        avlTree.insert(33);
        avlTree.insert(70);
        avlTree.insert(42);
        avlTree.insert(80);


        Object[] avlArray1 = avlTree.asArray();
        assertEquals(50, avlArray1[0]);
        assertEquals(40, avlArray1[1]);
        assertEquals(75, avlArray1[2]);
        assertEquals(30, avlArray1[3]);
        assertEquals(42, avlArray1[4]);
        assertEquals(60, avlArray1[5]);
        assertEquals(90, avlArray1[6]);
        assertEquals(25, avlArray1[7]);
        assertEquals(33, avlArray1[8]);
        assertEquals(41, avlArray1[9]);
        assertEquals(45, avlArray1[10]);
        assertEquals(55, avlArray1[11]);
        assertEquals(70, avlArray1[12]);
        assertEquals(80, avlArray1[13]);
        assertEquals(100, avlArray1[14]);

    }


    @Test()
    public void test() {

        AVLTree<Integer> avlTree = new AVLTree<>();

        avlTree.insert(100);
        avlTree.insert(200);
        avlTree.insert(300);
        // single left rotation

        avlTree.insert(250);
        avlTree.insert(275);
        // left right  rotation

        avlTree.insert(50);
        avlTree.insert(25);
        // right rotation

        avlTree.insert(75);
        avlTree.insert(90);
        // right left?



        Object[] avlArray = avlTree.asArray();
        for (int i=0; i < avlArray.length; i++) {
            System.out.println(avlArray[i]);
        }




        // avlTree.search(455);
        System.out.println("Done");


        //avlTree.getInsertionPoint(avlTree.root,3);
        //System.out.println(avlTree.max(2,2));




        // Print the tree
        //avlTree.printTree();
    }




    @Test
    public void insertTestRightHeavy() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        for (int i = 0; i < 100000; i++) {
            avlTree.insert(i);
        }

        int i = 0;
        for (int x : avlTree.getInorderNodes()) {
            assertEquals(i, x);
            i++;
        }
    }

    @Test
    public void insertTestLeftHeavy() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        for (int i = 100000; i >= 0; i--) {
            avlTree.insert(i);
        }

        int i = 0;
        for (int x : avlTree.getInorderNodes()) {
            assertEquals(i, x);
            i++;
        }
    }

    // TODO: Test the height of each node and make sure valid. >> iterator.

    @Test
    public void insertTestRandom() {

        // TODO: Figure out if it's okay to use collections stuff here.
        List<Integer> expected = new ArrayList<>();
        AVLTree<Integer> avlTree = new AVLTree<>();

        Random rng = new Random(1);

        // Sample randomly.
        for (int i = 0; i < 100000; i++) {


            int randNum = rng.nextInt();


            if (i % 5000 == 0) {
                System.out.println(randNum + " " + i);
            }

            avlTree.insert(randNum);

            if (!expected.contains(randNum)) {
                expected.add(randNum);
            }
        }


        // Sort
        Collections.sort(expected);
        int i = 0;
        for (int x : avlTree.getInorderNodes()) {
            assertEquals((int) expected.get(i), x);
            i++;
        }




    }










}