import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Shanshan Wen
 * @version 1.0
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 (including the empty 0
     * index) where n is the number of data in the passed in ArrayList (not
     * INITIAL_CAPACITY). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[2 * size + 1];
        for (int i = 1; i <= size; i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("Cannot add null data to the heap");
            }
            backingArray[i] = data.get(i - 1);
        }
        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }

    }
    /**
     * use the down-heap start from the index we pass in.
     * we maintain the heap property by recursively moving
     * the element at the given index down the heap until it meet
     * the max-heap property.
     *
     * @param index the index where the down-heap start
     */
    private void downHeap(int index) {
        if (index * 2 > size && index * 2 + 1 > size) {
            return;
        }
        if (backingArray[index * 2] == null && backingArray[index * 2 + 1] == null) {
            return;
        } else if (backingArray[index * 2] == null || backingArray[index * 2 + 1] == null){
            T max;
            int maxIndex;
            if (backingArray[index * 2] == null) {
                max = backingArray[index * 2 +  1];
                maxIndex = index * 2 +  1;
            } else {
                max = backingArray[index * 2];
                maxIndex = index * 2;
            }
            if (backingArray[index].compareTo(max) < 0) {
                backingArray[maxIndex] = backingArray[index];
                backingArray[index] = max;
                downHeap(maxIndex);
            }
        } else {
            T max;
            int maxIndex;
            max = backingArray[index * 2].compareTo(backingArray[index * 2 + 1]) > 0 ? backingArray[index * 2] : backingArray[index * 2 + 1];
            maxIndex = backingArray[index * 2].compareTo(backingArray[index * 2 + 1]) > 0 ? index * 2 : index * 2 + 1;
            if (backingArray[index].compareTo(max) < 0) {
                backingArray[maxIndex] = backingArray[index];
                backingArray[index] = max;
                downHeap(maxIndex);
            }
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to the heap!");
        }
            if (size == backingArray.length - 1) {
                T[] temp = (T[]) new Comparable[backingArray.length * 2];
                for (int i = 1; i <=size; i++) {
                    temp[i] = backingArray[i];
                }
                backingArray = temp;
            }
            backingArray[++size] = data;
            int childIndex = size;
            while (childIndex > 1 && backingArray[childIndex / 2].compareTo(backingArray[childIndex]) < 0) {
                T temp = backingArray[childIndex / 2];
                backingArray[childIndex / 2] = backingArray[childIndex];
                backingArray[childIndex] = temp;
                childIndex /= 2;
            }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty, cannot remove element from a empty heap");
        }

        T max = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        downHeap(1);
        size--;
        return max;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty, cannot find the max!");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
