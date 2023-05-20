package expression.generic.operations;

public class Divide<T> extends AbstractBinaryOperation<T> {
    public Divide(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, 2);
    }

    @Override
    public T getExpressionResult(T x, T y) {
        return getType().divide(x, y);
    }

    @Override
    public boolean isInverse() {
        return true;
    }

    @Override
    public String getOperationSymbol() {
        return "/";
    }

}
