package expression;

public class UnarySubstract extends UnaryOperation {
    public UnarySubstract(AllExpression expression) {
        super(expression);
    }

    public UnarySubstract(AllExpression expression, boolean addBrackets) {
        super(expression, addBrackets);
    }

    @Override
    public String getOperationSymbol() {
        return "-";
    }

    @Override
    public int getExpressionResult(int x) {
        return -x;
    }

    @Override
    public double getExpressionResult(double x) {
        return -x;
    }

}
