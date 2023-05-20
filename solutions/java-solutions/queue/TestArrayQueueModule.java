package queue;

public class TestArrayQueueModule {

    public static void fillEnqueue(int number) {
        for (int i = 0; i < number; i++) {
            ArrayQueueModule.enqueue("el " + i);
        }
    }

    public static void fillPush(int number) {
        for (int i = 0; i < number; i++) {
            ArrayQueueModule.push("el " + i);
        }
    }

    public static void dump() {
        System.out.println("size: " + ArrayQueueModule.size());
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " "
                            + ArrayQueueModule.element() + " "
                            + ArrayQueueModule.dequeue()
            );
        }
    }

    public static void dumpRemove() {
        System.out.println("size: " + ArrayQueueModule.size());
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " "
                            + ArrayQueueModule.peek() + " "
                            + ArrayQueueModule.remove()
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
        fillEnqueue(genNumber);
        if (ArrayQueueModule.size() != genNumber) {
            return "Exception in testEnqueue";
        }
        Object[] arr = ArrayQueueModule.toArray();
        for (int i = 0; i < genNumber; i++) {
            if (!arr[i].equals("el " + i)) {
                return "Exception in testEnqueue";
            }
        }
        ArrayQueueModule.clear();
        return "TestEnqueue was successfully passed";
    }

    public static String testPush(int genNumber) {
        fillPush(genNumber);
        if (ArrayQueueModule.size() != genNumber) {
            return "Exception in testPush";
        }
        Object[] arr = ArrayQueueModule.toArray();
        for (int i = 0; i < genNumber; i++) {
            if (!arr[genNumber - i - 1].equals("el " + i)) {
                return "Exception in testPush";
            }
        }
        ArrayQueueModule.clear();
        return "TestPush was successfully passed";
    }

    public static String testToArray(int genNumber) {
        fillEnqueue(genNumber);
        if (ArrayQueueModule.size() != genNumber) {
            return "Exception in testToArray";
        }
        Object[] arr = ArrayQueueModule.toArray();
        for (int i = 0; i < genNumber; i++) {
            if (!arr[i].equals("el " + i)) {
                return "Exception in testToArray";
            }
        }
        ArrayQueueModule.clear();
        return "TestToArray was successfully passed";
    }

    public static String testElementDequeue(int genNumber) {
        fillEnqueue(genNumber);
        if (ArrayQueueModule.size() != genNumber) {
            return "Exception in testElementDequeue";
        }
        for(int i = 0; i < genNumber; i++) {
            if(!ArrayQueueModule.element().equals("el " + i) ||
                    !ArrayQueueModule.dequeue().equals("el " + i)) {
                return "Exception in testElementDequeue";
            }
        }
        return "TestElementDequeue was successfully passed";
    }

    public static String testPeekRemove(int genNumber) {
        fillPush(genNumber);
        if (ArrayQueueModule.size() != genNumber) {
            return "Exception in testPeekRemove";
        }
        for(int i = 0; i < genNumber; i++) {
            if(!ArrayQueueModule.peek().equals("el " + i) ||
                    !ArrayQueueModule.remove().equals("el " + i)) {
                return "Exception in testPeekRemove";
            }
        }
        return "TestPeekRemove was successfully passed";
    }

    public static String testClear(int genNumber) {
        fillEnqueue(genNumber);
        ArrayQueueModule.clear();
        if (!ArrayQueueModule.isEmpty()) {
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
