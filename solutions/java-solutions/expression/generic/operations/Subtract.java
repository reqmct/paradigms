package expression.generic.operations;

public class Subtract<T> extends AbstractBinaryOperation<T> {
    public Subtract(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, 1);
    }

    @Override
    public T getExpressionResult(T x, T y) {
        return getType().subtract(x, y);
    }

    @Override
    public boolean isInverse() {
        return true;
    }

    @Override
    public String getOperationSymbol() {
        return "-";
    }

}
