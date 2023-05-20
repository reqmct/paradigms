package queue;

import java.util.Arrays;
import java.util.Objects;


/* Model a[start] ... a[end]
Inv: for i = start...end: a[i] != null
immut(start, end): for i=start..end: a'[i] == a[i]
 */
public class ArrayQueueADT {
    private int start;
    private int end;
    private int size;
    private Object[] elements = new Object[5];

    //Pred: element != null
    //Post: end' = end + 1 && a[end'] == element && immut(start, end)
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        if ((queue.end + 1) % queue.elements.length == queue.start) {
            ensureCapacity(queue);
        }
        queue.elements[queue.end] = element;
        queue.end = (queue.end + 1) % queue.elements.length;
        queue.size++;
    }

    //Pred: element != null
    //Post: start' = start - 1 && a[start'] == element && immut(start, end)
    public static void push(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        if ((queue.start - 1 + queue.elements.length) % queue.elements.length == queue.end) {
            ensureCapacity(queue);
        }
        queue.start = (queue.start - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.start] = element;
        queue.size++;
    }

    private static void ensureCapacity(ArrayQueueADT queue) {
        queue.elements = Arrays.copyOf(toArray(queue), 2 * size(queue));
        queue.start = 0;
        queue.end = queue.elements.length / 2;
    }

    //immut(start, end) R = {a[start], ..., a[end]}
    public static Object[] toArray(ArrayQueueADT queue) {
        Object[] current = new Object[size(queue)];
        if (queue.start <= queue.end) {
            System.arraycopy(queue.elements, queue.start, current, 0, size(queue));
        } else {
            System.arraycopy(queue.elements, queue.start, current,
                    0, queue.elements.length - queue.start);
            System.arraycopy(queue.elements, 0, current,
                    queue.elements.length - queue.start, queue.end);
        }
        return current;
    }

    //Pred: n >= 1
    //Post: R==a[end] && immut(start, end) && n' == n
    public static Object element(ArrayQueueADT queue) {
        assert size(queue) >= 1;
        return queue.elements[queue.start];
    }

    //Pred: n>=1
    //Post: n'== n-1 && R == a[start] && a[start] = null && start' = start + 1 && immut(start', end)
    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) >= 1;
        Object result = queue.elements[queue.start];
        queue.elements[queue.start] = null;
        queue.start = (queue.start + 1) % queue.elements.length;
        queue.size--;
        return result;
    }

    //Pred: n>=1
    //Post n' == n && R = a[end - 1]
    public static Object peek(ArrayQueueADT queue) {
        return queue.elements[(queue.end - 1 + queue.elements.length) % queue.elements.length];
    }

    //Pred: n>=1
    //Post: n' == n - 1 && R = a[end-1] && a[end-1] = null && end' = end-1 && immut(start,end')
    public static Object remove(ArrayQueueADT queue) {
        queue.end = (queue.end - 1 + queue.elements.length) % queue.elements.length;
        Object r = queue.elements[queue.end];
        queue.elements[queue.end] = null;
        queue.size--;
        return r;
    }

    //Pred: true
    //Post: R==n && n'==n && immut(sart, end)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    //Pred: true
    //Post: R==(n>0) && n' == n && immut(start, end)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }

    //Pred: true
    //Post: n' == 0 && i = start...end a[i] == null
    public static void clear(ArrayQueueADT queue) {
        queue.start = 0;
        queue.end = 0;
        queue.size = 0;
        queue.elements = new Object[5];
    }
}
