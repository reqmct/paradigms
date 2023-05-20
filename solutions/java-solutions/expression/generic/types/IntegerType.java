package expression.generic.types;

public class IntegerType extends NumberType<Integer> {
    private final boolean addOverflow;

    public IntegerType(boolean addOverflow) {
        this.addOverflow = addOverflow;
    }

    public IntegerType() {
        addOverflow = true;
    }

    @Override
    public Integer add(Integer x, Integer y) {
        if (addOverflow && x > 0 && y > 0 && x + y < 0) {
            throw new ArithmeticException("Overflow");
        }
        if (addOverflow && x < 0 && y < 0 && x + y >= 0) {
            throw new ArithmeticException("Overflow");
        }
        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (addOverflow && x >= 0 && y < 0 && x - y < 0) {
            throw new ArithmeticException("Overflow");
        }
        if (addOverflow && x < 0 && y > 0 && x - y > 0) {
            throw new ArithmeticException("Overflow");
        }
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        if (addOverflow && (x == 0 || y == 0)) {
            return 0;
        }
        if (addOverflow && (x * y / y != x || x * y / x != y)) {
            throw new ArithmeticException("Overflow");
        }
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (y == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (addOverflow && x == Integer.MIN_VALUE && y == -1) {
            throw new ArithmeticException("Overflow");
        }
        return x / y;
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        return x % y;
    }

    @Override
    public Integer abs(Integer x) {
        if (x < 0) {
            if (addOverflow && -x < 0) {
                throw new ArithmeticException("Overflow");
            }
            return -x;
        }
        return x;
    }

    @Override
    public Integer getZero() {
        return 0;
    }

    @Override
    public Integer parseType(String source) {
        return Integer.parseInt(source);
    }

    @Override
    public Integer parseType(int x) {
        return x;
    }

}
