package expression.exceptions;

import expression.Add;
import expression.AllExpression;


public class CheckedAdd extends Add {
    public CheckedAdd(AllExpression left, AllExpression right) {
        super(left, right);
    }

    @Override
    public int getExpressionResult(int x, int y) {
        if (x > 0 && y > 0 && x + y < 0) {
            throw new ArithmeticException("Overflow");
        }
        if (x < 0 && y < 0 && x + y >= 0) {
            throw new ArithmeticException("Overflow");
        }
        return super.getExpressionResult(x, y);
    }
}
