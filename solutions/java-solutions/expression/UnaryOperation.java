package expression;

public abstract class UnaryOperation implements AllExpression {
    public AllExpression expression;
    private boolean addBrackets = false;

    public UnaryOperation(AllExpression expression) {
        this.expression = expression;
    }

    public UnaryOperation(AllExpression expression, boolean addBrackets) {
        this.addBrackets = addBrackets;
        this.expression = expression;
        if (expression instanceof Primitive ||
                expression instanceof UnaryOperation) {
            this.addBrackets = false;
        }
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getExpressionResult(expression.evaluate(x, y, z));
    }

    public boolean getAddBrackets() {
        return addBrackets;
    }

    @Override
    public String toString() {
        return getOperationSymbol() + "(" + expression.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (!addBrackets) {
            return getOperationSymbol() + " " + expression.toMiniString();
        } else {
            return getOperationSymbol() + "(" + expression.toMiniString() + ")";
        }
    }

    @Override
    public int hashCode() {
        return getOperationSymbol().hashCode() * expression.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            return expression.equals(((UnaryOperation) obj).expression);
        }
        return false;
    }

    public abstract String getOperationSymbol();

    public abstract int getExpressionResult(int x);

    public abstract double getExpressionResult(double x);

}
