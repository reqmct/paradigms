package expression;

public class Clear extends BinaryOperation {
    public Clear(AllExpression left, AllExpression right) {
        super(left, right, 0);
    }

    @Override
    public String getOperationSymbol() {
        return "clear";
    }

    @Override
    public double getExpressionResult(double x, double y) {
        return 0;
    }

    @Override
    public int getExpressionResult(int x, int y) {
        return (x & ~(1 << y));
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
