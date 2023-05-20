package expression.generic.types;

public class ShortType extends NumberType<Short> {
    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short subtract(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short divide(Short x, Short y) {
        return (short) (x / y);
    }

    @Override
    public Short mod(Short x, Short y) {
        return (short) (x % y);
    }

    @Override
    public Short negate(Short x) {
        return (short) -x;
    }

    @Override
    public Short abs(Short x) {
        return (short) Math.abs(x);
    }

    @Override
    public Short getZero() {
        return (short) 0;
    }

    @Override
    public Short parseType(String source) {
        return Short.parseShort(source);
    }

    @Override
    public Short parseType(int x) {
        return (short) x;
    }
}
