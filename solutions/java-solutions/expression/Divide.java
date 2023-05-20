package expression;

public class Divide extends BinaryOperation {

    public Divide(AllExpression left, AllExpression right) {
        super(left, right, 2);
    }



    @Override
    public String getOperationSymbol() {
        return "/";
    }

    @Override
    public int getExpressionResult(int x, int y) {
        return x / y;
    }

    @Override
    public boolean isInverse() {
        return true;
    }

    @Override
    public boolean isResultInt() {
        return false;
    }

    @Override
    public double getExpressionResult(double x, double y) {
        return x / y;
    }
}
