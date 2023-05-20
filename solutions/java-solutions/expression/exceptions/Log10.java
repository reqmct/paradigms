package expression.exceptions;

import expression.AllExpression;
import expression.UnaryOperation;

public class Log10 extends UnaryOperation {

    public Log10(AllExpression expression) {
        super(expression,true);
    }

    @Override
    public String getOperationSymbol() {
        return "log10";
    }

    @Override
    public int getExpressionResult(int x) {
        if (x <= 0) {
            throw new IllegalArgumentException("Incorrect number format (Log10)");
        }
        int ans = 0;
        while (x >= 10) {
            x = x / 10;
            ans += 1;
        }
        return ans;
    }

    @Override
    public double getExpressionResult(double x) {
        return 0;
    }
}
