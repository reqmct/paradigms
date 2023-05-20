package expression.generic.operations;



public abstract class AbstractBinaryOperation<T> extends AbstractOperation<T> {
    private final TripleExpression<T> left;
    private final TripleExpression<T> right;
    private final int priority;


    protected AbstractBinaryOperation(TripleExpression<T> left, TripleExpression<T> right, int priority) {
        super(left.getType());
        this.left = left;
        this.right = right;
        this.priority = priority;
    }

    public TripleExpression<T> getLeft() {
        return left;
    }

    public TripleExpression<T> getRight() {
        return right;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return getExpressionResult(getLeft().evaluate(x, y, z), getRight().evaluate(x, y, z));
    }

    public abstract T getExpressionResult(T x, T y);

    @Override
    public String toString() {
        return "(" + getLeft().toString() + " " + getOperationSymbol() +
                " " + getRight().toString() + ")";
    }

    @Override
    public String toMiniString() {
        return toMiniString(false);
    }
    public String toMiniString(boolean addBrackets) {
        String result = getExpression(getLeft(), false) + " " + getOperationSymbol() + " "
                + getExpression(getRight(), true);
        if (!addBrackets) {
            return result;
        }
        return "(" + result + ")";
    }

    private String getExpression(TripleExpression<T> element, boolean isRight) {
        if (element instanceof AbstractBinaryOperation<T> binaryElement) {
            if (binaryElement.getPriority() == getPriority()) {
                if (isRight && isInverse()) {
                    return binaryElement.toMiniString(true);
                }
            }
            if (binaryElement.getPriority() < this.getPriority()) {
                return binaryElement.toMiniString(true);
            }
        }
        return element.toMiniString();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public abstract boolean isInverse();
}
