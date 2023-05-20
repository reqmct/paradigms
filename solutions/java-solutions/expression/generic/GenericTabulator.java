package expression.generic;

import expression.generic.operations.TripleExpression;
import expression.generic.parser.ExpressionParser;
import expression.generic.types.*;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private final Map<String, Type<?>> types = Map.of(
            "i", new IntegerType(),
            "d", new DoubleType(),
            "bi", new BigIntegerType(),
            "u", new IntegerType(false),
            "l", new LongType(),
            "s", new ShortType()
    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return getResult(types.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] getResult(Type<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws Exception {
        TripleExpression<T> parseExpression = new ExpressionParser<T>().parse(expression, type);
        Object[][][] answer = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        answer[x - x1][y - y1][z - z1] = parseExpression.evaluate(
                                type.parseType(x),
                                type.parseType(y),
                                type.parseType(z));
                    } catch (IllegalArgumentException | ArithmeticException e) {
                        answer[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return answer;
    }
}
