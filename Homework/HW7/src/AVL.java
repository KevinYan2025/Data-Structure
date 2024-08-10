import java.util.Collection;

import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Zhixiang Yan
 * @version 1.0
 * @userid Zyan319
 * @GTID 903810954
 *
 * Collaborators:
 *
 * Resources:
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null in the AVL!");
        }
        this.size = 0;
        for (T eachData:data) {
            if (eachData == null) {
                this.size = 0;
                throw new IllegalArgumentException("Data cannot be null in the AVL!");
            }
            add(eachData);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data into AVL tree!");
        }
        root = addHelper(data, root);
    }
    /**
     * private helper function to traverse through the AVL and insert it into the tree
     *
     * @param data the data to add
     * @param curr The current node we are at
     * @return the Node
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            this.size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        }

        int height = (height(curr.getLeft())
                > height(curr.getRight()) ? height(curr.getLeft()) : height(curr.getRight())) + 1;
        curr.setHeight(height);
        int bf = balanceFactor(curr);
        if (bf > 1 && data.compareTo(curr.getLeft().getData()) < 0) {
            return rightRotate(curr);
        }
        if (bf < -1 && data.compareTo(curr.getRight().getData()) > 0) {
            return leftRotate(curr);
        }
        if (bf > 1 && data.compareTo(curr.getLeft().getData()) > 0) {
            curr.setLeft(leftRotate(curr.getLeft()));
            return rightRotate(curr);
        }
        if (bf < -1 && data.compareTo(curr.getRight().getData()) < 0) {
            curr.setRight(rightRotate(curr.getRight()));
            return leftRotate(curr);
        }

        return curr;
    }
    /**
     * private helper function to rotate the node in the left direction
     *
     * @param curr The current node we are at
     * @return the AVL after being left rotated
     */
    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getRight();
        curr.setRight(temp.getLeft());
        temp.setLeft(curr);
        curr.setHeight((height(curr.getLeft()) > height(curr.getRight())
                ? height(curr.getLeft()) : height(curr.getRight())) + 1);
        temp.setHeight((height(temp.getLeft()) > height(temp.getRight())
                ? height(temp.getLeft()) : height(temp.getRight())) + 1);
        return temp;
    }
    /**
     * private helper function to rotate the node in the right direction
     *
     * @param curr The current node we are at
     * @return the AVL after being right rotated
     */
    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getLeft();
        curr.setLeft(temp.getRight());
        temp.setRight(curr);
        curr.setHeight((height(curr.getLeft()) > height(curr.getRight())
                ? height(curr.getLeft()) : height(curr.getRight())) + 1);
        temp.setHeight((height(temp.getLeft()) > height(temp.getRight())
                ? height(temp.getLeft()) : height(temp.getRight())) + 1);
        return temp;
    }
    /**
     * private helper function to find the curr node's height
     *
     * @param curr The current node we are at
     * @return the height
     */
    private int height(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        return curr.getHeight();
    }
    /**
     * private helper function to calculate the curr balanceFactor of AVL
     *
     * @param curr The current node we are at
     * @return the balanceFactor
     */
    private int balanceFactor(AVLNode<T> curr) {
        if (curr == null) {
            return 0;
        }
        return height(curr.getLeft()) - height(curr.getRight());
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null and cannot be removed from AVL.");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        if (removed.getData() == null) {
            throw new NoSuchElementException("Data not found in AVL.");
        }
        return removed.getData();
    }
    /**
     * remove helper function for to remove the node recusively and update height and balance factory if needed.
     *
     * @param data the data to remove
     * @param curr Current AVLNode
     * @param dummy AVLNode to store the remove node
     * @return node after begin remove
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> curr,  AVLNode<T> dummy) {
        if (curr == null) {
            dummy.setData(null);
            return null;
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(data, curr.getRight(), dummy));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(data, curr.getLeft(), dummy));
        } else {
            size--;
            dummy.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        int height = (height(curr.getLeft()) > height(curr.getRight())
                ? height(curr.getLeft()) : height(curr.getRight())) + 1;
        curr.setHeight(height);
        int bf = balanceFactor(curr);
        if (bf > 1 && data.compareTo(curr.getLeft().getData()) < 0) {
            return rightRotate(curr);
        }
        if (bf < -1 && data.compareTo(curr.getRight().getData()) > 0) {
            return leftRotate(curr);
        }
        if (bf > 1 && data.compareTo(curr.getLeft().getData()) > 0) {
            curr.setLeft(leftRotate(curr.getLeft()));
            return rightRotate(curr);
        }
        if (bf < -1 && data.compareTo(curr.getRight().getData()) < 0) {
            curr.setRight(rightRotate(curr.getRight()));
            return leftRotate(curr);
        }


        return curr;
    }
    /**
     * second remove helper method use to remove the predecessor node and return the node to remove
     *
     * @param curr Current BSTNode
     * @param dummy AVLNode to store the remove node
     * @return the updated subtree after the successor has been removed.
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return  curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy));
        }
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot retrieve null data from AVL!");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        AVLNode<T> pointer = root;
        getHelper(data, pointer, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("no such data found in the AVL");
        }
        return dummy.getData();
    }
    /**
     * get helper method use to traverse through the AVL and search for the data
     *
     * @param data data we need to find and remove
     * @param curr current AVLNode we are at
     * @param dummy use to store the  AVLNode value we need to remove
     */
    private void getHelper(T data, AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr == null) {
            return;
        } else if (data.compareTo(curr.getData()) > 0) {
            getHelper(data, curr.getRight(), dummy);
        } else if (data.compareTo(curr.getData()) < 0) {
            getHelper(data, curr.getLeft(), dummy);
        } else {
            dummy.setData(curr.getData());
            return;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data will not be in the AVL!");
        }
        AVLNode<T> dummy = root;
        return containsHelper(data, dummy);
    }
    /**
     * contains helper method use to traverse through the AVL and check whether the data exist in the AVL
     *
     * @param data the dat to search for
     * @param curr the current node we are at
     * @return true if AVL contain the data we pass in, false otherwise.
     */
    private boolean containsHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) == 0) {
            return true;
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(data, curr.getRight());
        }
        return containsHelper(data, curr.getLeft());
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data in AVL cannot be null.");
        }

        AVLNode<T> predecessorNode = findPredecessor(root, data, null);
        if (predecessorNode == null) {
            throw new NoSuchElementException("Data is not in the AVL");
        }

        return predecessorNode.getData();
    }
    /**
     * private helper method use to recursively find the predecessor
     *
     * @param data the dat to search for
     * @param predecessor the AVL node use to store the predecessor
     * @param curr the current node we are at
     * @return the current AVL node
     */
    private AVLNode<T> findPredecessor(AVLNode<T> curr, T data, AVLNode<T> predecessor) {
        if (curr == null) {
            return predecessor;
        }

        int result = data.compareTo(curr.getData());

        if (result == 0) {
            if (curr.getLeft() != null) {
                return findRightmostNode(curr.getLeft());
            }
        } else if (result < 0) {
            return findPredecessor(curr.getLeft(), data, predecessor);
        } else {
            predecessor = curr;
            return findPredecessor(curr.getRight(), data, predecessor);
        }

        return predecessor;
    }
    /**
     * private helper method use to find the rightmost node in the curr
     *
     * @param curr the current node we are at
     * @return the rightmost AVL node in the curr's subtree
     */
    private AVLNode<T> findRightmostNode(AVLNode<T> curr) {
        if (curr.getRight() == null) {
            return curr;
        }
        return findRightmostNode(curr.getRight());
    }
    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        }
        AVLNode<T> deepestNode = findDeepestNode(root, height(root));
        return deepestNode.getData();
    }
    /**
     * findDeepestNode helper method use to traverse through the AVL find the deepestNode in the AVL
     *
     * @param currentHeight The current height
     * @param curr the current node we are at
     * @return the deepest node in AVL
     */
    private AVLNode<T> findDeepestNode(AVLNode<T> curr, int currentHeight) {
        if (curr == null) {
            return null;
        }
        if (currentHeight == 0) {
            return curr;
        }
        AVLNode<T> leftDeepest = findDeepestNode(curr.getLeft(), currentHeight - 1);
        AVLNode<T> rightDeepest = findDeepestNode(curr.getRight(), currentHeight - 1);
        if (leftDeepest != null && rightDeepest != null) {
            return rightDeepest;
        } else if (leftDeepest != null) {
            return leftDeepest;
        } else {
            return rightDeepest;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
