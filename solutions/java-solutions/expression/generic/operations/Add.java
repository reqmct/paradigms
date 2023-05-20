package expression.generic.operations;


public class Add<T> extends AbstractBinaryOperation<T> {

    public Add(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right, 1);
    }

    @Override
    public T getExpressionResult(T x, T y) {
        return getType().add(x, y);
    }

    @Override
    public boolean isInverse() {
        return false;
    }

    @Override
    public String getOperationSymbol() {
        return "+";
    }

}
