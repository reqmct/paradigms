package expression.generic.operations;



public class Negate<T> extends AbstractUnaryOperation<T> {
    public Negate(TripleExpression<T> exp) {
        super(exp);
    }

    public Negate(TripleExpression<T> exp, boolean addBrackets) {
        super(exp, addBrackets);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getOperationSymbol() {
        return "-";
    }

    @Override
    public T getExpressionResult(T x) {
        return getType().negate(x);
    }
}
