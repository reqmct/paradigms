package expression.generic.types;

public class LongType extends NumberType<Long> {
    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long divide(Long x, Long y) {
        return x / y;
    }

    @Override
    public Long mod(Long x, Long y) {
        return x % y;
    }

    @Override
    public Long abs(Long x) {
        return Math.abs(x);
    }

    @Override
    public Long getZero() {
        return (long) 0;
    }

    @Override
    public Long parseType(String source) {
        return Long.parseLong(source);
    }

    @Override
    public Long parseType(int x) {
        return (long) x;
    }
}
