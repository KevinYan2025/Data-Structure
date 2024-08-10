import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Zhixiang Yan
 * @version 1.0
 * @userid zyan319
 * @GTID 903810954
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        this.size = 0;
        for (T eachData:data) {
            if (eachData == null) {
                this.size = 0;
                throw new IllegalArgumentException("Data cannot be null in the BST!");
            }
            add(eachData);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into BST!");
        }
        root = addHelper(data, root);
    }
    /**
     * private helper function to traverse through the BST and insert it into the tree
     *
     * @param data the data to add
     * @param curr The current node we are at
     * @return the Node
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            this.size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove a null data from BST!");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeHelper(data, root, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("No data found in the BST!");
        }
        this.size -= 1;
        return dummy.getData();

    }
    /**
     * remove helper function for the node containing the data is a leaf (no children). In this case,
     * simply remove it.
     *
     * @param data the data to remove
     * @param curr Current BSTNode
     * @param dummy BSTNode to store the remove node
     * @return node after begin remove
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> curr,  BSTNode<T> dummy) {
        if (curr == null) {
            dummy.setData(null);
            return null;
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(data, curr.getRight(), dummy));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(data, curr.getLeft(), dummy));
        } else {
            dummy.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }
    /**
     * second remove helper method use to remove the successor node and return the node to remove
     *
     * @param curr Current BSTNode
     * @param dummy2 BSTNode to store the remove node
     * @return the updated subtree after the successor has been removed.
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy2) {
        if (curr.getLeft() == null) {
            dummy2.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy2));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot retrieve null data from BST!");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        BSTNode<T> pointer = root;
        getHelper(data, pointer, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("no such data found in the BST");
        }
        return dummy.getData();
    }
    /**
     * get helper method use to traverse through the BST and search for the data
     *
     * @param data data we need to find and remove
     * @param curr current BSTNode we are at
     * @param dummy use to store the  BSTNode value we need to remove
     */
    private void getHelper(T data, BSTNode<T> curr, BSTNode<T> dummy) {
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
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data will not be in the BST!");
        }
        BSTNode<T> dummy = root;
        return containsHelper(data, dummy);
    }

    /**
     * contains helper method use to traverse through the BST and check whether the data exist in the BST
     *
     * @param data the dat to search for
     * @param curr the current node we are at
     * @return true if BST contain the data we pass in, false otherwise.
     */
    private boolean containsHelper(T data, BSTNode<T> curr) {
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<>();
        BSTNode<T> dummy = root;
        preorderHelper(dummy, list);
        return list;
    }

    /**
     * preorder helper method use to traverse the BST and add data to the list
     *
     * @param curr the current BSTNode we are at
     * @param list The list use to store the BSTNode data in preorder.
     */
    private void preorderHelper(BSTNode<T> curr, ArrayList<T> list) {
        if (curr == null) {
            return;
        } else {
            list.add(curr.getData());
            preorderHelper(curr.getLeft(), list);
            preorderHelper(curr.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<>();
        BSTNode<T> dummy = root;
        inorderHelper(dummy, list);
        return list;
    }
    /**
     * inorder helper method use to traverse the BST and add data to the list
     *
     * @param curr the current BSTNode we are at
     * @param list The list use to store the BSTNode data in inorder.
     */
    private void inorderHelper(BSTNode<T> curr, ArrayList<T> list) {
        if (curr == null) {
            return;
        } else {
            inorderHelper(curr.getLeft(), list);
            list.add(curr.getData());
            inorderHelper(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<>();
        BSTNode<T> dummy = root;
        postorderHelper(dummy, list);
        return list;
    }
    /**
     * postorder helper method use to traverse the BST and add data to the list
     *
     * @param curr the current BSTNode we are at
     * @param list The list use to store the BSTNode data in postorder.
     */
    private void postorderHelper(BSTNode<T> curr, ArrayList<T> list) {
        if (curr == null) {
            return;
        } else {
            postorderHelper(curr.getLeft(), list);
            postorderHelper(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> que = new LinkedList<>();
        ArrayList<T> list = new ArrayList<>();
        que.offer(root);
        while (!que.isEmpty()) {
            BSTNode<T> temp = que.poll();
            list.add(temp.getData());
            if (temp.getLeft() != null) {
                que.offer(temp.getLeft());
            }
            if (temp.getRight() != null) {
                que.offer(temp.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {

        return heightHelper(root);

    }

    /**
     * height helper method
     *
     *
     * @param curr the current BSTNode we are at
     * @return the height of the tree
     */
    private int heightHelper(BSTNode<T> curr) {

        if (curr == null) {
            return -1;
        }
        int leftHeight = heightHelper(curr.getLeft());
        int rightHeight = heightHelper(curr.getRight());

        return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        this.size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data input cannot be null");
        }
        List<T> path = new LinkedList<>();
        if (data1.equals(data2)) {
            path.add(data1);
        } else {
            BSTNode<T> dca = findDCA(root, data1, data2);
            if (dca == null) {
                throw new NoSuchElementException("Data1 or Data2 is not int the tree!");
            }
            path = findFirstLeg(data1, dca, (LinkedList<T>) path);
            path = findSecondLeg(data2, dca, (LinkedList<T>) path);
        }
        return path;
    }

    /**
     * Recursive helper method to find the Deepest Common Ancestor (DCA)
     * of data1 and data2.
     * The DCA is defined as the lowest node in the tree that possesses both
     * data1 and data2 as children in its subtree.
     *
     * @param data1 the first data input
     * @param data2 the second data input
     * @param curr the root node we are traverse
     * @return the node for the Deepest Common Ancestor
     */
    private BSTNode<T> findDCA(BSTNode<T> curr, T data1, T data2) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data1) > 0 && curr.getData().compareTo(data2) < 0
            || curr.getData().compareTo(data1) < 0 && curr.getData().compareTo(data2) > 0) {
            return curr;
        } else if (curr.getData().compareTo(data1) > 0 && curr.getData().compareTo(data2) > 0) {
            return findDCA(curr.getLeft(), data1, data2);
        } else if (curr.getData().compareTo(data1) < 0 && curr.getData().compareTo(data2) < 0) {
            return findDCA(curr.getRight(), data1, data2);
        } else if (curr.getData().compareTo(data1) == 0 || curr.getData().compareTo(data2) == 0) {
            return curr;
        }
        return null;
    }

    /**
     * Recursive helper method to find the path from data1 to the Deepest Common Ancestor (DCA),
     * adding all the nodes to the front of the list.
     *
     * @param data1 the first data
     * @param curr the Deepest common ancestor node being searched through currently
     * @param path the path we need to find between those two data input
     * @throws NoSuchElementException if the data1 is not found in the tree
     * @return the list of all nodes between the DCA and the data, excluding
     * the DCA
     */
    private List<T> findFirstLeg(T data1, BSTNode<T> curr, LinkedList<T> path) {
        if (curr != null) {
            if (data1.compareTo(curr.getData()) == 0) {
                path.addFirst(curr.getData());
                path.removeLast();
                return path;
            } else if (data1.compareTo(curr.getData()) < 0) {
                path.addFirst(curr.getData());
                return findFirstLeg(data1, curr.getLeft(), path);
            } else {
                path.addFirst(curr.getData());
                return findFirstLeg(data1, curr.getRight(), path);
            }
        }
        throw new NoSuchElementException("Data1 is not in the tree!");
    }

    /**
     * Recursive helper method to find the second half of the path from data2 to the Deepest Common Ancestor (DCA),
     * adding all the nodes to the back of the list.
     *
     * @param data2 the second data
     * @param curr the Deepest common ancestor node being searched through currently
     * @param path the path we need to find between those two data input
     * @throws NoSuchElementException if the data2 is not found in the tree
     * @return the list of all nodes between the DCA and the data, including
     * the DCA
     */
    private List<T> findSecondLeg(T data2, BSTNode<T> curr, LinkedList<T> path) {
        if (curr != null) {
            if (data2.compareTo(curr.getData()) == 0) {
                path.addLast(curr.getData());
                return path;
            } else if (data2.compareTo(curr.getData()) < 0) {
                path.addLast(curr.getData());
                return findSecondLeg(data2, curr.getLeft(), path);
            } else {
                path.addLast(curr.getData());
                return findSecondLeg(data2, curr.getRight(), path);
            }
        }
        throw new NoSuchElementException("Data2 is not in the tree!");
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
