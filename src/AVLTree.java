import java.util.LinkedList;
import java.util.List;

/**
 * Create an AVL tree to store a key value. Duplicates keys are ignored. Space complexity: O(n),
 * where n is the number of nodes in the tree.
 *
 * @param <K> The key value to store in the tree. Must have the Comparable interface implemented.
 * @author Aaron Mussig (https://github.com/aaronmussig)
 */
public class AVLTree<K extends Comparable<? super K>> {

    AVLNode root;    // The root of the tree.
    int size;        // The number of nodes in the tree.

    /**
     * Instantiate a new AVL Tree.
     * Time complexity: O(1), as only a single variable is accessed.
     */
    public AVLTree() {
        this.size = 0;
    }

    /**
     * @return The number of nodes in the tree.
     * Time complexity: O(1), as only a single variable is accessed.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Time complexity: O(n), where n is the number of nodes in the tree.
     *
     * @return A binary array representation of the Tree.
     */
    public Object[] asArray() {

        // Setup the output array.
        Object[] output = new Object[(int) Math.pow(2, this.root.getHeight()) - 1];
        output[0] = this.root.getKey();

        // Traverse through the tree in breadth first search order.
        LinkedList<AVLNode> queue = new LinkedList<>();
        queue.add(this.root);
        int counter = 1;
        while (!queue.isEmpty()) {

            // Register the visit.
            AVLNode currentNode = queue.poll();
            AVLNode leftChild = currentNode.getLeft();
            AVLNode rightChild = currentNode.getRight();
            if (leftChild != null) {
                queue.add(leftChild);
                output[2 * counter - 1] = currentNode.getLeft().getKey();
            }
            if (rightChild != null) {
                queue.add(rightChild);
                output[2 * counter] = currentNode.getRight().getKey();
            }
            counter++;
        }
        return output;
    }

    /**
     * Time complexity: O(n), where n is the number of nodes in the tree.
     *
     * @return A list of all in-order nodes.
     */
    public List<K> getInorderNodes() {
        return traverseInorder(this.root);
    }

    /**
     * The helper function to getInorderNodes. Time complexity: O(n), where n is the number of nodes
     * in the tree.
     *
     * @param rootNode The node to start traversal from.
     * @return A list of all in-order nodes.
     */
    private List<K> traverseInorder(AVLNode rootNode) {
        List<K> userList = new LinkedList<>();

        // Traverse down each branch of the tree and store the visit.
        if (rootNode != null) {
            userList.addAll(traverseInorder(rootNode.getLeft()));
            /*
            for (K curKey : traverseInorder(rootNode.getLeft())) {
                userList.add(curKey);
            }
            */
            userList.add(rootNode.getKey());

            userList.addAll(traverseInorder(rootNode.getRight()));
            /*
            for (K curKey : traverseInorder(rootNode.getRight())) {
                userList.add(curKey);
            }
            */
        }
        return userList;
    }

    /**
     * Time complexity: O(n), where n is the number of nodes in the tree.
     *
     * @return The string representation of the AVL Tree as: [K|K|K]
     */
    @Override
    public String toString() {

        // Get all keys.
        List<K> keys = traverseInorder(this.root);
        StringBuilder output = new StringBuilder();
        output.append("[");

        // Only display the first 10 keys.
        int i = 1;
        int max = Math.min(keys.size(), 10);
        for (K key : keys) {
            output.append(String.valueOf(key));

            if (i < max) {
                output.append("|");
            } else {

                if (i != keys.size()) {
                    output.append("...");
                }
                break;
            }
            i++;
        }
        output.append("]");
        return output.toString();
    }

    /**
     * Add a new element to the AVL tree. Duplicate keys are ignored. Time complexity: O(log(n))
     * where n is the number of nodes in the tree, as the tree is a complete and balanced binary
     * search tree.
     *
     * @param key The key to insert.
     */
    public void insert(K key) {

        // Create a new node.
        AVLNode newNode = new AVLNode(key);

        // Set this as the root node if the tree hasn't been instantiated.
        if (this.root == null) {
            this.root = newNode;
        }

        // Tree has already been instantiated, do a normal insertion.
        else {
            // Traverse the tree to find the insertion point.
            AVLNode insertionPoint = getInsertionPoint(key);

            // An insertion point has been found.
            if (insertionPoint != null) {

                // This is to be the left child.
                if (key.compareTo(insertionPoint.getKey()) < 0) {
                    insertionPoint.setLeft(newNode);
                    newNode.setParent(insertionPoint);
                }

                // This is to be the right child.
                else if (key.compareTo(insertionPoint.getKey()) > 0) {
                    insertionPoint.setRight(newNode);
                    newNode.setParent(insertionPoint);
                }
            } else {
                // Duplicates are not allowed.
                return;
            }
        }

        // Re-calculate the heights of the subtrees and balance.
        balanceTree(newNode);
        this.size++;
    }

    /**
     * Searches the tree and returns the value associated with this key. Time complexity: O(log(n))
     * where n is the number of nodes in the tree, as the tree is a complete and balanced binary
     * search tree.
     *
     * @param key The key to search for.
     * @return True if the key is found, false otherwise.
     */
    public boolean contains(K key) {
        AVLNode currentNode = this.root;

        // Traverse through the tree in breadth first search order.
        while (currentNode != null) {
            if (key.compareTo(currentNode.getKey()) < 0) {
                currentNode = currentNode.getLeft();     // Go down the left subtree.
            } else if (key.compareTo(currentNode.getKey()) > 0) {
                currentNode = currentNode.getRight();    // Go down the right subtree.
            } else {
                return true;                             // The key was found.
            }
        }
        // Element was not found in the tree.
        return false;
    }

    /**
     * Recurse up the tree from the leaf node and update height balance. Time complexity: O(log(n))
     * where n is the number of nodes in the tree, as the tree is a complete and balanced binary
     * search tree. Traversal occurs up the tree.
     *
     * @param leafNode The node to start traversing upwards from.
     */
    private void balanceTree(AVLNode leafNode) {
        AVLNode currentNode = leafNode;
        AVLNode z = null;

        // Traverse up and identify the nodes X,Y,Z.
        while (currentNode != null) {

            // Update the height of this subtree.
            currentNode.updateHeight();

            // Check if the subtree is balanced.
            int balance = currentNode.getBalance();

            // Set Z to be the first node which is unbalanced.
            if ((balance < -1 || balance > 1) && z == null) {
                z = currentNode;

                // Identify Y (child of Z with largest height).
                AVLNode y = (z.getLeftHeight() > z.getRightHeight()) ? z.getLeft() : z.getRight();

                // Identify X (child of Y with largest height).
                AVLNode x = (y.getLeftHeight() > y.getRightHeight()) ? y.getLeft() : y.getRight();

                // Determine the in-order listing of the nodes X,Y,Z.
                AVLNode a = null;
                AVLNode b = null;
                AVLNode c = null;
                AVLNode t0 = null;
                AVLNode t1 = null;
                AVLNode t2 = null;
                AVLNode t3 = null;

                // Do a right balance.
                if (y.compareTo(z) < 0 && x.compareTo(y) < 0) {
                    a = x;
                    b = y;
                    c = z;
                    t0 = x.getLeft();
                    t1 = x.getRight();
                    t2 = y.getRight();
                    t3 = z.getRight();
                }

                // Do a left/right balance.
                else if (y.compareTo(z) < 0 && x.compareTo(y) > 0) {
                    a = y;
                    b = x;
                    c = z;
                    t0 = y.getLeft();
                    t1 = x.getLeft();
                    t2 = x.getRight();
                    t3 = z.getRight();
                }

                // Do a right/left balance.
                else if (y.compareTo(z) > 0 && x.compareTo(y) < 0) {
                    a = z;
                    b = x;
                    c = y;
                    t0 = z.getLeft();
                    t1 = x.getLeft();
                    t2 = x.getRight();
                    t3 = y.getRight();
                }

                // Do a left balance.
                else if (y.compareTo(z) > 0 && x.compareTo(y) > 0) {
                    a = z;
                    b = y;
                    c = x;
                    t0 = z.getLeft();
                    t1 = y.getLeft();
                    t2 = x.getLeft();
                    t3 = x.getRight();
                }

                // Replace the subtree rooted at z with a new subtree rooted at b
                if (z.getParent() == null) {
                    this.root = b;
                } else if (z.getParent().getLeft() == z) {
                    z.getParent().setLeft(b);
                } else {
                    z.getParent().setRight(b);
                }
                b.setParent(z.getParent());

                // a is the left child of b and t0, t1 are the left and right subtrees respectively.
                b.setLeft(a);
                a.setParent(b);

                a.setLeft(t0);
                if (t0 != null) {
                    t0.setParent(a);
                }

                a.setRight(t1);
                if (t1 != null) {
                    t1.setParent(a);
                }

                // c is the right child of b and t2, t3 are left and right subtrees respectively.
                b.setRight(c);
                c.setParent(b);

                c.setLeft(t2);
                if (t2 != null) {
                    t2.setParent(c);
                }

                c.setRight(t3);
                if (t3 != null) {
                    t3.setParent(c);
                }

                a.updateHeight();
                b.updateHeight();
                c.updateHeight();
            }

            // Move to the parent.
            currentNode = currentNode.getParent();
        }
    }

    /**
     * Given a key value, find the insertion point which would be the direct parent of the new Node.
     * Time complexity: O(log(n)) where n is the number of nodes in the tree, as the tree is a
     * complete and balanced binary search tree.
     *
     * @param key The key of the new Node to be probed.
     * @return The Node representing the insertion point.
     */
    private AVLNode getInsertionPoint(K key) {
        AVLNode previousNode = this.root;
        AVLNode currentNode = this.root;

        // Search through the tree in breadth first search order.
        while (currentNode != null) {

            // Go down left subtree.
            if (key.compareTo(currentNode.getKey()) < 0) {
                previousNode = currentNode;
                currentNode = currentNode.getLeft();
            }

            // Go down right subtree.
            else if (key.compareTo(currentNode.getKey()) > 0) {
                previousNode = currentNode;
                currentNode = currentNode.getRight();
            }

            // Duplicate found.
            else {
                return null;
            }
        }
        return previousNode;
    }

    /**
     * A Node in the Tree which contains the key/value pair. Space complexity: O(1) as a constant
     * number of pointers are stored.
     *
     * @author Aaron Mussig (https://github.com/aaronmussig)
     */
    class AVLNode implements Comparable<AVLNode> {

        private int height;                     // Height of this node.
        private AVLNode left, right, parent;    // Left and right children.
        private K key;                          // The key of this Node.

        /**
         * Instantiate a new node in the tree.
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @param key The key of this node.
         */
        private AVLNode(K key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The height of this node in the Tree.
         */
        private int getHeight() {
            return this.height;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The height of the left child.
         */
        private int getLeftHeight() {
            return (this.left == null) ? 0 : this.left.getHeight();
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The height of the right child.
         */
        private int getRightHeight() {
            return (this.right == null) ? 0 : this.right.getHeight();
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * Update the height of this node.
         */
        private void updateHeight() {
            int leftHeight = getLeftHeight();
            int rightHeight = getRightHeight();
            this.height = Math.max(leftHeight, rightHeight) + 1;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The balance of this node.
         */
        private int getBalance() {
            return getLeftHeight() - getRightHeight();
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The parent of this node.
         */
        private AVLNode getParent() {
            return this.parent;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @param parent The new parent of this node.
         */
        private void setParent(AVLNode parent) {
            this.parent = parent;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The key associated with this Node.
         */
        K getKey() {
            return this.key;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The left child of this node.
         */
        AVLNode getLeft() {
            return this.left;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @param child The left child to be set.
         */
        private void setLeft(AVLNode child) {
            this.left = child;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The right child of this node.
         */
        AVLNode getRight() {
            return this.right;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @param child The right child to be set.
         */
        private void setRight(AVLNode child) {
            this.right = child;
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @return The string representation of this node.
         */
        @Override
        public String toString() {
            return String.valueOf(this.key);
        }

        /**
         * Time complexity: O(1), as a constant number of references are accessed.
         *
         * @param that The node to compare to.
         * @return -1 if this is less than that, 1 if this is greater than that, 0 otherwise.
         */
        @Override
        public int compareTo(AVLNode that) {
            return this.getKey().compareTo(that.getKey());
        }
    }
}