package expression;

public class Set extends BinaryOperation {
    public Set(AllExpression left, AllExpression right) {
        super(left, right, 0);
    }

    @Override
    public String getOperationSymbol() {
        return "set";
    }

    @Override
    public double getExpressionResult(double x, double y) {
        return 0;
    }

    @Override
    public int getExpressionResult(int x, int y) {
        return (x | (1 << y));
    }

    @Override
    public boolean isInverse() {
        return true;
    }

    @Override
    public boolean isResultInt() {
        return true;
    }
}
