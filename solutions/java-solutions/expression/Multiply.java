package expression;

public class Multiply extends BinaryOperation {
    public Multiply(AllExpression left, AllExpression right) {
        super(left, right, 2);
    }


    @Override
    public String getOperationSymbol() {
        return "*";
    }

    @Override
    public int getExpressionResult(int x, int y) {
        return x * y;
    }

    @Override
    public boolean isInverse() {
        return false;
    }

    @Override
    public boolean isResultInt() {
        return true;
    }

    @Override
    public double getExpressionResult(double x, double y) {
        return x * y;
    }
}
