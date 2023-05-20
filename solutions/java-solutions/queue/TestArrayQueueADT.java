package queue;


public class TestArrayQueueADT {
    public static void fillEnqueue(ArrayQueueADT queue, int number) {
        for (int i = 0; i < number; i++) {
            ArrayQueueADT.enqueue(queue,"el " + i);
        }
    }

    public static void fillPush(ArrayQueueADT queue, int number) {
        for (int i = 0; i < number; i++) {
            ArrayQueueADT.push(queue,"el " + i);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        System.out.println("size: " + ArrayQueueADT.size(queue));
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " "
                            + ArrayQueueADT.element(queue) + " "
                            + ArrayQueueADT.dequeue(queue)
            );
        }
    }

    public static void dumpRemove(ArrayQueueADT queue) {
        System.out.println("size: " + ArrayQueueADT.size(queue));
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " "
                            + ArrayQueueADT.peek(queue) + " "
                            + ArrayQueueADT.remove(queue)
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
        ArrayQueueADT q = new ArrayQueueADT();
        fillEnqueue(q, genNumber);
        if (ArrayQueueADT.size(q) != genNumber) {
            return "Exception in testEnqueue";
        }
        Object[] arr = ArrayQueueADT.toArray(q);
        for (int i = 0; i < genNumber; i++) {
            if (!arr[i].equals("el " + i)) {
                return "Exception in testEnqueue";
            }
        }
        ArrayQueueADT.clear(q);
        return "TestEnqueue was successfully passed";
    }

    public static String testPush(int genNumber) {
        ArrayQueueADT q = new ArrayQueueADT();
        fillPush(q, genNumber);
        if (ArrayQueueADT.size(q) != genNumber) {
            return "Exception in testPush";
        }
        Object[] arr = ArrayQueueADT.toArray(q);
        for (int i = 0; i < genNumber; i++) {
            if (!arr[genNumber - i - 1].equals("el " + i)) {
                return "Exception in testPush";
            }
        }
        ArrayQueueADT.clear(q);
        return "TestPush was successfully passed";
    }

    public static String testToArray(int genNumber) {
        ArrayQueueADT q = new ArrayQueueADT();
        fillEnqueue(q, genNumber);
        if (ArrayQueueADT.size(q) != genNumber) {
            return "Exception in testToArray";
        }
        Object[] arr = ArrayQueueADT.toArray(q);
        for (int i = 0; i < genNumber; i++) {
            if (!arr[i].equals("el " + i)) {
                return "Exception in testToArray";
            }
        }
        ArrayQueueADT.clear(q);
        return "TestToArray was successfully passed";
    }

    public static String testElementDequeue(int genNumber) {
        ArrayQueueADT q = new ArrayQueueADT();
        fillEnqueue(q, genNumber);
        if (ArrayQueueADT.size(q) != genNumber) {
            return "Exception in testElementDequeue";
        }
        for(int i = 0; i < genNumber; i++) {
            if(!ArrayQueueADT.element(q).equals("el " + i) ||
                    !ArrayQueueADT.dequeue(q).equals("el " + i)) {
                return "Exception in testElementDequeue";
            }
        }
        return "TestElementDequeue was successfully passed";
    }

    public static String testPeekRemove(int genNumber) {
        ArrayQueueADT q = new ArrayQueueADT();
        fillPush(q, genNumber);
        if (ArrayQueueADT.size(q) != genNumber) {
            return "Exception in testPeekRemove";
        }
        for(int i = 0; i < genNumber; i++) {
            if(!ArrayQueueADT.peek(q).equals("el " + i) ||
                    !ArrayQueueADT.remove(q).equals("el " + i)) {
                return "Exception in testPeekRemove";
            }
        }
        return "TestPeekRemove was successfully passed";
    }

    public static String testClear(int genNumber) {
        ArrayQueueADT q = new ArrayQueueADT();
        fillEnqueue(q, genNumber);
        ArrayQueueADT.clear(q);
        if (!ArrayQueueADT.isEmpty(q)) {
            return "Exception in testClear";
        }
        return "TestClear was successfully passed";
    }

    public static void main(String[] args) {
        System.out.println(testClear(1000));
        System.out.println(testToArray(1000));
        System.out.println(testEnqueue(1000));
        System.out.println(testPush(1000));
        System.out.println(testElementDequeue(1000));
        System.out.println(testPeekRemove(1000));
    }
}
