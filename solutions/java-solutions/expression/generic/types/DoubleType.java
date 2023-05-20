package expression.generic.types;

public class DoubleType extends NumberType<Double> {
    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double subtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double mod(Double x, Double y) {
        return x % y;
    }

    @Override
    public Double abs(Double x) {
        return Math.abs(x);
    }

    @Override
    public Double getZero() {
        return 0.0;
    }

    @Override
    public Double parseType(String source) {
        return Double.parseDouble(source);
    }

    @Override
    public Double parseType(int x) {
        return (double) x;
    }
}
