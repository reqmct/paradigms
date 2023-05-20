package queue;

import java.util.Arrays;
import java.util.Objects;

/* Model a[start] ... a[end]
Inv: for i = start...end: a[i] != null
immut(start, end): for i=start..end: a'[i] == a[i]
 */
public class ArrayQueueModule {
    private static int start;
    private static int end;
    private static int size;
    private static Object[] elements = new Object[5];

    //Pred: element != null
    //Post: end' = end + 1 && a[end'] == element && immut(start, end)
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        if ((end + 1) % elements.length == start) {
            ensureCapacity();
        }
        elements[end] = element;
        end = (end + 1) % elements.length;
        size++;
    }

    //Pred: element != null
    //Post: start' = start - 1 && a[start'] == element && immut(start, end)
    public static void push(Object element) {
        if ((start - 1 + elements.length) % elements.length == end) {
            ensureCapacity();
        }
        start = (start - 1 + elements.length) % elements.length;
        elements[start] = element;
        size++;
    }

    private static void ensureCapacity() {
        elements = Arrays.copyOf(toArray(), 2 * size());
        start = 0;
        end = elements.length / 2;
    }

    //immut(start, end) R = {a[start], ..., a[end]}
    public static Object[] toArray() {
        Object[] current = new Object[size()];
        if (start <= end) {
            System.arraycopy(elements, start, current, 0, size());
        } else {
            System.arraycopy(elements, start, current, 0, elements.length - start);
            System.arraycopy(elements, 0, current, elements.length - start, end);
        }
        return current;
    }

    //Pred: n >= 1
    //Post: R==a[end] && immut(start, end) && n' == n
    public static Object element() {
        assert size() >= 1;
        return elements[start];
    }

    //Pred: n>=1
    //Post: n'== n-1 && R == a[start] && a[start] = null && start' = start + 1 && immut(start', end)
    public static Object dequeue() {
        assert size() >= 1;
        Object result = elements[start];
        elements[start] = null;
        start = (start + 1) % elements.length;
        size--;
        return result;
    }

    //Pred: n>=1
    //Post n' == n && R = a[end - 1]
    public static Object peek() {
        return elements[(end - 1 + elements.length) % elements.length];
    }


    //Pred: n>=1
    //Post: n' == n - 1 && R = a[end-1] && a[end-1] = null && end' = end-1 && immut(start,end')
    public static Object remove() {
        end = (end - 1 + elements.length) % elements.length;
        Object r = elements[end];
        elements[end] = null;
        size--;
        return r;
    }

    //Pred: true
    //Post: R==n && n'==n && immut(sart, end)
    public static int size() {
        return size;
    }

    //Pred: true
    //Post: R==(n>0) && n' == n && immut(start, end)
    public static boolean isEmpty() {
        return size() == 0;
    }

    //Pred: true
    //Post: n' == 0 && i = start...end a[i] == null
    public static void clear() {
        start = 0;
        end = 0;
        size = 0;
        elements = new Object[5];
    }
}
