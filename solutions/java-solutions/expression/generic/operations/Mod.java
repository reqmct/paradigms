package expression.generic.operations;


public class Mod<T> extends AbstractBinaryOperation<T> {
    public Mod(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, 2);
    }

    @Override
    public T getExpressionResult(T x, T y) {
        return getType().mod(x, y);
    }

    @Override
    public boolean isInverse() {
        return false;
    }

    @Override
    public String getOperationSymbol() {
        return "mod";
    }
}
