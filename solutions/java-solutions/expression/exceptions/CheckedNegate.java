package expression.exceptions;

import expression.AllExpression;
import expression.UnarySubstract;

public class CheckedNegate extends UnarySubstract {

    public CheckedNegate(AllExpression expression) {
        super(expression);
    }

    public CheckedNegate(AllExpression expression, boolean addBrackets) {
        super(expression, addBrackets);
    }

    @Override
    public int getExpressionResult(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow");
        }
        return super.getExpressionResult(x);
    }
}
