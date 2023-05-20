package expression;

public abstract class BinaryOperation implements AllExpression {
    private final int priority;
    private final AllExpression left;
    private final AllExpression right;

    public BinaryOperation(AllExpression left, AllExpression right, int priority) {
        this.priority = priority;
        this.left = left;
        this.right = right;
    }

    public AllExpression getLeft() {
        return left;
    }

    public AllExpression getRight() {
        return right;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int hashCode() {
        return getLeft().hashCode() * getOperationSymbol().hashCode() * getOperationSymbol().hashCode()
                + getRight().hashCode() * getOperationSymbol().hashCode() + getOperationSymbol().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            return getLeft().equals(((BinaryOperation) obj).getLeft())
                    && getRight().equals(((BinaryOperation) obj).getRight());
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + getLeft().toString() + " " + getOperationSymbol() +
                " " + getRight().toString() + ")";
    }

    @Override
    public String toMiniString() {
        return toMiniString(false);
    }

    private String getExpression(AllExpression element, boolean isRight) {
        if (element instanceof BinaryOperation binaryElement) {
            if (binaryElement.getPriority() == getPriority()) {
                if (isRight && isInverse()) {
                    return binaryElement.toMiniString(true);
                }
                if (isRight && !binaryElement.isResultInt()
                        && this.isResultInt()) {
                    return binaryElement.toMiniString(true);
                }
            }
            if (binaryElement.getPriority() < this.getPriority()) {
                return binaryElement.toMiniString(true);
            }
        }
        return element.toMiniString();
    }

    public String toMiniString(boolean addBrackets) {
        String result = getExpression(getLeft(), false) + " " + getOperationSymbol() + " "
                + getExpression(getRight(), true);
        if (addBrackets == false) {
            return result;
        }
        return "(" + result + ")";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getExpressionResult(getLeft().evaluate(x, y, z), getRight().evaluate(x, y, z));
    }

    public abstract String getOperationSymbol();


    public abstract double getExpressionResult(double x, double y);

    public abstract int getExpressionResult(int x, int y);

    public abstract boolean isInverse();
    public abstract boolean isResultInt();

}
