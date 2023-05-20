package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        assert size() >= 1;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        assert size() >= 1;
        size--;
        return dequeueImpl();
    }

    public abstract Object dequeueImpl();

    public void clear() {
        size = 0;
        clearImpl();
    }

    public abstract void clearImpl();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Object element) {
        assert element != null;
        if (isEmpty()) {
            return false;
        }
        resetInd();
        while (!getInd().equals(getEnd())) {
            if (indValueEquals(element)) {
                break;
            }
            next();
        }
        return indValueEquals(element);
    }


    protected abstract void next();

    protected abstract Object getInd();

    protected abstract boolean indValueEquals(Object element);

    protected abstract void resetInd();

    protected abstract Object getEnd();

    public boolean removeFirstOccurrence(Object element) {
        assert element != null;
        if (isEmpty()) {
            return false;
        }
        if (!contains(element)) {
            return false;
        }
        size--;
        return removeFirstOccurrenceImpl(element);
    }

    protected abstract boolean removeFirstOccurrenceImpl(Object element);
}
