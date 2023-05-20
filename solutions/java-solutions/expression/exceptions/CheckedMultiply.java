package expression.exceptions;

import expression.AllExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    public int getExpressionResult(int x, int y) {
        if (x == 0 || y == 0) {
            return 0;
        }
        if (x * y / y != x || x * y / x != y) {
            throw new ArithmeticException("Overflow");
        }
        return super.getExpressionResult(x, y);
    }
}
