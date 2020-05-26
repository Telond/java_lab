package com.company;

import java.io.PrintStream;


public abstract class AbstractHash {

    /**
     * Sream data
     **/
    protected PrintStream out;


    /**
     * Method to get the field value {@link AbstractHash#out}
     *
     * @return out stream value
     */
    public abstract PrintStream getOutStream();

    /**
     * Output stream definition method
     *
     * @param out Output steam
     */
    public abstract void setOutStream(PrintStream out);

    public static void swap(Integer[] array, Integer i, Integer j) {
        Integer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public Integer[] sort(Integer[] array) {
        int h = 1;
        while (h * 3 < array.length)
            h = h * 3 + 1;

        while (h >= 1) {
            hSort(array, h);
            h = h / 3;
        }
        return array;
    }

    private void hSort(Integer[] array, Integer h) {
        int length = array.length;
        for (int i = h; i < length; i++) {
            for (int j = i; j >= h; j = j - h) {
                if (array[j] < array[j - h])
                    swap(array, j, j - h);
                else
                    break;
            }
        }
    }

    public static void quickSort(Integer[] array, int low, int high) {
        if (array.length == 0)
            return;

        if (low >= high)
            return;


        int middle = low + (high - low) / 2;
        int opora = array[middle];

        int i = low, j = high;
        while (i <= j) {
            while (array[i] < opora) {
                i++;
            }

            while (array[j] > opora) {
                j--;
            }
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        if (low < j)
            quickSort(array, low, j);

        if (high > i)
            quickSort(array, i, high);
    }



    public void sorts(Integer[] a)
    {
        introsort_loop(a, 0, a.length, 2*floor_lg(a.length));
    }



    private static void introsort_loop (Integer[] a, int lo, int hi, int depth_limit)
    {

        int size_threshold = 16;
        while (hi-lo > size_threshold)
        {
            if (depth_limit == 0)
            {
                heapsort(a, lo, hi);
                return;
            }
            depth_limit=depth_limit-1;
            int p=partition(a, lo, hi, medianof3(a, lo, lo+((hi-lo)/2)+1, hi-1));
            introsort_loop(a, p, hi, depth_limit);
            hi=p;
        }
        insertionsort(a, lo, hi);
    }

    private static int partition(Integer[] a, int lo, int hi, int x)
    {
        int i=lo, j=hi;
        while (true)
        {
            while (a[i] < x) i++;
            j=j-1;
            while (x < a[j]) j=j-1;
            if(!(i < j))
                return i;
            exchange(a,i,j);
            i++;
        }
    }

    private static int medianof3(Integer[] a, int lo, int mid, int hi)
    {
        if (a[mid] < a[lo])
        {
            if (a[hi] < a[mid])
                return a[mid];
            else
            {
                if (a[hi] < a[lo])
                    return a[hi];
                else
                    return a[lo];
            }
        }
        else
        {
            if (a[hi] < a[mid])
            {
                if (a[hi] < a[lo])
                    return a[lo];
                else
                    return a[hi];
            }
            else
                return a[mid];
        }
    }


    private static void heapsort(Integer[] a, int lo, int hi)
    {
        int n = hi-lo;
        for (int i=n/2; i>=1; i=i-1)
        {
            downheap(a,i,n,lo);
        }
        for (int i=n; i>1; i=i-1)
        {
            exchange(a,lo,lo+i-1);
            downheap(a,1,i-1,lo);
        }
    }

    private static void downheap(Integer[] a, int i, int n, int lo)
    {
        int d = a[lo+i-1];
        int child;
        while (i<=n/2)
        {
            child = 2*i;
            if (child < n && a[lo+child-1] < a[lo+child])
            {
                child++;
            }
            if (d >= a[lo+child-1]) break;
            a[lo+i-1] = a[lo+child-1];
            i = child;
        }
        a[lo+i-1] = d;
    }


    private static void insertionsort(Integer[] a, int lo, int hi)
    {
        int i,j;
        int t;
        for (i=lo; i < hi; i++)
        {
            j=i;
            t = a[i];
            while(j!=lo && t < a[j-1])
            {
                a[j] = a[j-1];
                j--;
            }
            a[j] = t;
        }
    }


    private static void exchange(Integer[] a, int i, int j)
    {
        int t=a[i];
        a[i]=a[j];
        a[j]=t;
    }

    private static int floor_lg(Integer a)
    {
        return (int)(Math.floor(Math.log(a)/Math.log(2)));
    }


}
