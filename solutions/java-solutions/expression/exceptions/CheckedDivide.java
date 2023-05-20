package expression.exceptions;

import expression.AllExpression;
import expression.Divide;

public class CheckedDivide extends Divide {
    public CheckedDivide(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    public int getExpressionResult(int x, int y) {
        if (y == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new ArithmeticException("Overflow");
        }
        return super.getExpressionResult(x, y);
    }
}
