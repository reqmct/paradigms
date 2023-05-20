package expression.generic.operations;

public abstract class AbstractUnaryOperation<T> extends AbstractOperation<T> {
    private final TripleExpression<T> exp;
    private boolean addBrackets = false;
    protected AbstractUnaryOperation(TripleExpression<T> exp) {
        super(exp.getType());
        this.exp = exp;
    }

    protected AbstractUnaryOperation(TripleExpression<T> exp, boolean addBrackets) {
        super(exp.getType());
        this.addBrackets = addBrackets;
        this.exp = exp;
        if (exp instanceof AbstractUnaryOperation<T>) {
            this.addBrackets = false;
        }
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return getExpressionResult(exp.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return getOperationSymbol() + "(" + exp.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (!addBrackets) {
            return getOperationSymbol() + " " + exp.toMiniString();
        } else {
            return getOperationSymbol() + "(" + exp.toMiniString() + ")";
        }
    }

    public abstract T getExpressionResult(T x);
}
