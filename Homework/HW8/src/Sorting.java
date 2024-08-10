import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Arra");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j], arr[j-1]) < 0) {
                T temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        int lastswapMade = 0;
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i],arr[i+1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                    lastswapMade = i;
                }
            }
            end = lastswapMade;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i],arr[i-1]) < 0) {
                    T temp = arr[i];
                    arr[i] = arr[i-1];
                    arr[i-1] = temp;
                    lastswapMade = i;
                }
            }
            start = lastswapMade;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if ( arr == null || comparator == null) {
            throw new IllegalArgumentException("s");
        }
        if (arr.length == 1 || arr.length == 0) {
            return;
        }
        int length = arr.length;
        int mid = length/2;
        T[] left = mergeHelper(arr,0,mid - 1);
        T[] right =mergeHelper(arr,mid,length - 1);
        mergeSort(left,comparator);
        mergeSort(right,comparator);
        int i = 0;
        int  j =0;
        while(i < left.length && j < right.length) {
            if (comparator.compare(left[i],right[j]) < 0) {
                arr[i+j] = left[i++];
            } else {
                arr[i+j]=right[j++];
            }
        }
        while(i <left.length) {
            arr[i+j] = left[i++];
        }
        while(j <right.length) {
            arr[i+j] = right[j++];
        }


    private static <T> T[] mergeHelper(T[] arr, int left, int right) {
        T  newArr[]= (T[])new Object[right-left+1];
        int j = 0;
        for (int i = left; i<= right;i++) {
            newArr[j++] = arr[i];
        }
        return  newArr;

    }
    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        quickSortHelper(arr, 0, arr.length - 1, comparator, rand);
    }
    /** quickSort helper function that recursively sorting the arr using in place sorting and utilize pitvot.
     * @param arr        the array that need to sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
      * @param <T>        data type to sort
      * @param startInd the starting point of the subarray
      * @param endInd the ending point of the subarray
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
            */
    private static <T> void quickSortHelper(T[] arr, int startInd, int endInd, Comparator<T> comparator, Random rand) {
        if (endInd - startInd < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(endInd - startInd + 1) + startInd;
        T temp = arr[pivotIndex];
        arr[pivotIndex] = arr[startInd];
        arr[startInd] = temp;
        int i = startInd + 1;
        int j =endInd;
        while (i < j) {
            while (i < j &&comparator.compare(arr[startInd],arr[i]) > 0) {
                i++;
            }
            while(i < j && comparator.compare(arr[startInd],arr[j]) < 0) {
                j--;
            }
            if (i < j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j]= temp;
                i++;
                j--;
            }
        }
        T temp = arr[startInd];
        arr[startInd] = arr[j];
        arr[j] = temp;
        quickSortHelper(arr,startInd,j,comparator,rand);
        quickSortHelper(arr,j)
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        int max = longestNumber(arr);
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        int k = countDigits(max);
        int numberDivide = 1;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arr.length; j++) {
                int currentDigits = (arr[j] / numberDivide) % 10;
                buckets[currentDigits + 9].add(arr[j]);
            }
            numberDivide *= 10;
            int index = 0;
            for (LinkedList<Integer> bucket: buckets) {
                while (!bucket.isEmpty()) {
                    arr[index++] = bucket.removeFirst();
                }
            }
        }
    }
    /**
     *  Radix sort helper function to find the longestNumber
     *
     * @param num the number to count
     *
     * @return the longest number in arr
     */
    private static int countDigits(int num) {
        if (num == 0) {
            return 1;
        }
        int count = 0;
        while (num != 0) {
            num = num / 10;
            count++;
        }
        return count;
    }
    /**
     *  Radix sort helper function to find the longestNumber
     *
     * @param arr the array we using to find the longestNumber
     *
     * @return the longest number in arr
     */
    private static int longestNumber(int[] arr) {
        int longestNum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (longestNum < arr[i]) {
                longestNum = arr[i];
            }
        }
        return longestNum;
    }
    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        int[] arr = new int[data.size()];
        PriorityQueue<Integer> minQue = new PriorityQueue<Integer>();
        for (Integer eachData : data) {
            minQue.add(eachData);
        }
        int index = 0;
        while (!minQue.isEmpty()) {
            arr[index++] = minQue.remove();
        }
        return arr;
    }
}
