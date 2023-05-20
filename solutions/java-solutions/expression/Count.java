package expression;

public class Count extends UnaryOperation {
    public Count(AllExpression expression) {
        super(expression, true);
    }

    @Override
    public String getOperationSymbol() {
        return "count";
    }

    @Override
    public int getExpressionResult(int x) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            count += ((x >> i) & 1);
        }
        return count;
    }

    @Override
    public double getExpressionResult(double x) {
        return 0;
    }
}
