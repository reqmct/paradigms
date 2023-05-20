package expression.generic.operations;

public class Multiply<T> extends AbstractBinaryOperation<T> {
    public Multiply(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, 2);
    }

    @Override
    public T getExpressionResult(T x, T y) {
        return getType().multiply(x, y);
    }

    @Override
    public boolean isInverse() {
        return false;
    }

    @Override
    public String getOperationSymbol() {
        return "*";
    }

}
