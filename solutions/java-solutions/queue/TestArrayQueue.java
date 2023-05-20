package queue;


public class TestArrayQueue {
    public static void fillEnqueue(ArrayQueue queue, int number) {
        for (int i = 0; i < number; i++) {
            queue.enqueue("el " + i);
        }
    }

    public static void fillPush(ArrayQueue queue, int number) {
        for (int i = 0; i < number; i++) {
            queue.push("el " + i);
        }
    }

    public static void dump(ArrayQueue queue) {
        System.out.println("size: " + queue.size());
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " "
                            + queue.element() + " "
                            + queue.dequeue()
            );
        }
    }

    public static void dumpRemove(ArrayQueue queue) {
        System.out.println("size: " + queue.size());
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " "
                            + queue.peek() + " "
                            + queue.remove()
            );
        }
    }

    public static void dumpArray(Object[] array) {
        System.out.println("size: " + array.length);
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + " " + array[i]);
        }
    }

    public static String testEnqueue(int genNumber) {
        ArrayQueue q = new ArrayQueue();
        fillEnqueue(q, genNumber);
        if (q.size() != genNumber) {
            return "Exception in testEnqueue";
        }
        Object[] arr = q.toArray();
        for (int i = 0; i < genNumber; i++) {
            if (!arr[i].equals("el " + i)) {
                return "Exception in testEnqueue";
            }
        }
        q.clear();
        return "TestEnqueue successfully passed";
    }

    public static String testPush(int genNumber) {
        ArrayQueue q = new ArrayQueue();
        fillPush(q, genNumber);
        if (q.size() != genNumber) {
            return "Exception in testPush";
        }
        Object[] arr = q.toArray();
        for (int i = 0; i < genNumber; i++) {
            if (!arr[genNumber - i - 1].equals("el " + i)) {
                return "Exception in testPush";
            }
        }
        q.clear();
        return "TestPush successfully passed";
    }

    public static String testToArray(int genNumber) {
        ArrayQueue q = new ArrayQueue();
        fillEnqueue(q, genNumber);
        if (q.size() != genNumber) {
            return "Exception in testToArray";
        }
        Object[] arr = q.toArray();
        for (int i = 0; i < genNumber; i++) {
            if (!arr[i].equals("el " + i)) {
                return "Exception in testToArray";
            }
        }
        q.clear();
        return "TestToArray successfully passed";
    }

    public static String testElementDequeue(int genNumber) {
        ArrayQueue q = new ArrayQueue();
        fillEnqueue(q, genNumber);
        if (q.size() != genNumber) {
            return "Exception in testElementDequeue";
        }
        for(int i = 0; i < genNumber; i++) {
            if(!q.element().equals("el " + i) ||
                    !q.dequeue().equals("el " + i)) {
                return "Exception in testElementDequeue";
            }
        }
        return "TestElementDequeue successfully passed";
    }

    public static String testPeekRemove(int genNumber) {
        ArrayQueue q = new ArrayQueue();
        fillPush(q, genNumber);
        if (q.size() != genNumber) {
            return "Exception in testPeekRemove";
        }
        for(int i = 0; i < genNumber; i++) {
            if(!q.peek().equals("el " + i) ||
                    !q.remove().equals("el " + i)) {
                return "Exception in testPeekRemove";
            }
        }
        return "TestPeekRemove successfully passed";
    }

    public static String testClear(int genNumber) {
        ArrayQueue q = new ArrayQueue();
        fillEnqueue(q, genNumber);
        q.clear();
        if (!q.isEmpty()) {
            return "Exception in testClear";
        }
        return "TestClear successfully passed";
    }

    public static void main(String[] args) {
        System.out.println(testClear(10000000));
        System.out.println(testToArray(10000));
        System.out.println(testEnqueue(1000));
        System.out.println(testPush(1000));
        System.out.println(testElementDequeue(1000));
        System.out.println(testPeekRemove(1000));
    }
}
