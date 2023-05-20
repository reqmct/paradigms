package expression.generic.operations;

public class Abs<T> extends AbstractUnaryOperation<T> {
    public Abs(TripleExpression<T> exp) {
        super(exp);
    }

    protected Abs(TripleExpression<T> exp, boolean addBrackets) {
        super(exp, addBrackets);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getOperationSymbol() {
        return "abs";
    }

    @Override
    public T getExpressionResult(T x) {
        return getType().abs(x);
    }
}
