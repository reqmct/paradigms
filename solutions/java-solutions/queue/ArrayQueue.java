package queue;

import java.util.Arrays;

/* Model a[start] ... a[end]
Inv: for i = start...end: a[i] != null
immut(start, end): for i=start..end: a'[i] == a[i]
 */
public class ArrayQueue extends AbstractQueue {

    private int start;
    private int end;
    private int ind;
    private Object[] elements = new Object[5];

    public void enqueueImpl(Object element) {
        if ((end + 1) % elements.length == start) {
            ensureCapacity();
        }
        elements[end] = element;
        end = (end + 1) % elements.length;
        size++;
    }

    //Pred: element != null
    //Post: start' = start - 1 && a[start'] == element && immut(start, end)
    public void push(Object element) {
        if ((start - 1 + elements.length) % elements.length == end) {
            ensureCapacity();
        }
        start = (start - 1 + elements.length) % elements.length;
        elements[start] = element;
        size++;
    }

    private void ensureCapacity() {
        elements = Arrays.copyOf(toArray(), 2 * size());
        start = 0;
        end = elements.length / 2;
    }

    //immut(start, end) R = {a[start], ..., a[end]}
    public Object[] toArray() {
        Object[] current = new Object[size()];
        if (start <= end) {
            System.arraycopy(elements, start, current, 0, size());
        } else {
            System.arraycopy(elements, start, current, 0, elements.length - start);
            System.arraycopy(elements, 0, current, elements.length - start, end);
        }
        return current;
    }

    public Object elementImpl() {
        return elements[start];
    }

    public Object dequeueImpl() {
        Object result = elements[start];
        elements[start] = null;
        start = (start + 1) % elements.length;
        return result;
    }

    //Pred: n>=1
    //Post n' == n && R = a[end - 1]
    public Object peek() {
        return elements[(end - 1 + elements.length) % elements.length];
    }

    //Pred: n>=1
    //Post: n' == n - 1 && R = a[end-1] && a[end-1] = null && end' = end-1 && immut(start,end')
    public Object remove() {
        end = (end - 1 + elements.length) % elements.length;
        Object r = elements[end];
        elements[end] = null;
        size--;
        return r;
    }

    public void clearImpl() {
        start = 0;
        end = 0;
        elements = new Object[5];
    }

    @Override
    protected void next() {
        ind = (ind + 1) % elements.length;
    }
    @Override
    protected void resetInd() {
        ind = start;
    }
    @Override
    protected Object getInd() {
        return ind;
    }

    @Override
    protected boolean indValueEquals(Object element) {
        if(ind == end) {
            return false;
        }
        return elements[ind].equals(element);
    }
    @Override
    protected Object getEnd() {
        return end;
    }

    protected boolean removeFirstOccurrenceImpl(Object element) {
        ind = (ind + 1) % elements.length;
        while (ind != end) {
            int prefInd = (ind - 1 + elements.length) % elements.length;
            elements[prefInd] = elements[ind];
            ind = (ind + 1) % elements.length;
        }
        end = (end - 1 + elements.length) % elements.length;
        return true;
    }
}