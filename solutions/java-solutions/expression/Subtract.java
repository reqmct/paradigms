package expression;

public class Subtract extends BinaryOperation {
    public Subtract(AllExpression left, AllExpression right) {
        super(left, right, 1);
    }


    @Override
    public String getOperationSymbol() {
        return "-";
    }

    @Override
    public int getExpressionResult(int x, int y) {
        return x - y;
    }

    @Override
    public boolean isInverse() {
        return true;
    }

    @Override
    public boolean isResultInt() {
        return true;
    }

    @Override
    public double getExpressionResult(double x, double y) {
        return x - y;
    }
}
