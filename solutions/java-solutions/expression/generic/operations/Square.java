package expression.generic.operations;

public class Square<T> extends AbstractUnaryOperation<T>{
    public Square(TripleExpression<T> exp) {
        super(exp);
    }

    protected Square(TripleExpression<T> exp, boolean addBrackets) {
        super(exp, addBrackets);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getOperationSymbol() {
        return "square";
    }

    @Override
    public T getExpressionResult(T x) {
        return getType().square(x);
    }
}
