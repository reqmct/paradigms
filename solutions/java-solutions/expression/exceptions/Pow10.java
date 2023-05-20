package expression.exceptions;


import expression.AllExpression;
import expression.UnaryOperation;

public class Pow10 extends UnaryOperation {

    public Pow10(AllExpression expression) {
        super(expression, true);
    }

    @Override
    public String getOperationSymbol() {
        return "pow10";
    }

    @Override
    public int getExpressionResult(int x) {
        if (x >= 10) {
            throw new ArithmeticException("Overflow");
        }
        if (x < 0) {
            throw new IllegalArgumentException("Incorrect number format (Pow10)");
        }
        int ans = 1;
        while (x != 0) {
            ans = ans * 10;
            x--;
        }
        return ans;
    }

    @Override
    public double getExpressionResult(double x) {
        return 0;
    }
}
