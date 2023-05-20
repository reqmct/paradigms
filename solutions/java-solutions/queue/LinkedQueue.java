package queue;

public class LinkedQueue extends AbstractQueue {
    private Node start;
    private Node end;

    private Node ind;
    private Node prefInd;


    protected void enqueueImpl(Object element) {
        Node tmp = new Node(element);
        if (size == 0) {
            end = tmp;
            start = tmp;
        } else {
            end.next = tmp;
            end = tmp;
        }
        size++;
    }

    public Object elementImpl() {
        return start.value;
    }

    public Object dequeueImpl() {
        Object result = start.value;
        start = start.next;
        return result;
    }


    public void clearImpl() {
        start = null;
        end = null;
    }

    @Override
    protected void next() {
        prefInd = ind;
        ind = ind.next;
    }
    @Override
    protected void resetInd() {
        ind = start;
        prefInd = start;
    }
    @Override
    protected Object getInd() {
        return ind;
    }

    @Override
    protected boolean indValueEquals(Object element) {
        return ind.value.equals(element);
    }
    @Override
    protected Object getEnd() {
        return end;
    }

    protected boolean removeFirstOccurrenceImpl(Object element) {
        if (ind.equals(end)) {
            prefInd.next = prefInd;
            end = prefInd;
        } else if (ind.equals(start)) {
            start = start.next;
        } else {
            prefInd.next = ind.next;
        }
        return true;
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object value) {
            assert value != null;

            this.value = value;
            this.next = this;
        }
    }
}
