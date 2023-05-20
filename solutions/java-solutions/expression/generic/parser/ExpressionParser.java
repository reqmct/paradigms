package expression.generic.parser;

import expression.exceptions.BaseParser;
import expression.exceptions.Pow10;
import expression.exceptions.StringSource;
import expression.generic.types.Type;
import expression.generic.operations.*;

import java.util.Arrays;
import java.util.List;

public class ExpressionParser<T> extends BaseParser implements TripleParser<T> {
    private final List<String> notPrimitive = Arrays.asList("-", "+", "/", "*", ")",
            "mod", "\0");

    private void skipSpace() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }


    private void isCorrectOperation(final String name) {
        if (Character.isLetter((ch)) || Character.isDigit(ch)) {
            throw new IllegalArgumentException("Unexpected character: " + ch + ". There is no space after " + name + ". ");
        }
        if (ch == '\0') {
            throw new IllegalArgumentException("Unexpected character: " + ch + ". There is no argument in " + name + ". ");
        }
        if (!(Character.isWhitespace(ch) || ch == '-' || ch == '(')) {
            throw new IllegalArgumentException("Unexpected character: " + ch + ". Incorrect argument of " + name + ". ");
        }
    }

    private TripleExpression<T> priority1(final String errorMessage, Type<T> type) {
        skipSpace();
        TripleExpression<T> expression = priority2(errorMessage, type);
        skipSpace();
        while (test('+') || test('-')) {
            if (expect("+")) {
                expression = new Add<>(expression, priority2("There is no right argument. ", type));
            } else if (expect("-")) {
                expression = new Subtract<>(expression, priority2("There is no right argument. ", type));
            }
            skipSpace();
        }
        return expression;
    }

    private TripleExpression<T> priority2(final String errorMessage, Type<T> type) {
        skipSpace();
        TripleExpression<T> expression = primitive(errorMessage, type);
        skipSpace();
        while (test('*') || test('/') || test('m')) {
            if (expect("*")) {
                expression = new Multiply<>(expression, primitive("There is no right argument. ", type));
            } else if (expect("/")) {
                expression = new Divide<>(expression, primitive("There is no right argument. ", type));
            } else if (expect("mod")) {
                expression = new Mod<>(expression, primitive("There is no right argument. ", type));
            }
            skipSpace();
        }
        return expression;
    }

    private TripleExpression<T> primitive(String errorMessage, Type<T> type) {
        skipSpace();
        if (test('x') || test('y') || test('z')) {
            return new Variable<>(take(), type);
        }
        if (between('0', '9')) {
            StringBuilder number = new StringBuilder();
            boolean hasPeriod = false;
            while (between('0', '9') || (test('.') && !hasPeriod)) {
                if (test('.')) {
                    hasPeriod = true;
                }
                number.append(take());
            }
            return new Const<>(type.parseType(number.toString()), type);
        }
        if (take('(')) {
            TripleExpression<T> expression = priority1("Incorrect expression in parentheses. ", type);
            if (!test(')')) {
                throw new IllegalArgumentException("Unexpected character: " + take()
                        + ". There is no right bracket ");
            }
            take();
            return expression;
        }
        if (expect("abs")) {
            isCorrectOperation("abs");
            return new Abs<>(primitive("Incorrect argument of abs. ", type));
        }
        if (expect("square")) {
            isCorrectOperation("square");
            return new Square<>(primitive("Incorrect argument of square. ", type));
        }
        if (take('-')) {
            if (between('0', '9')) {
                StringBuilder number = new StringBuilder("-");
                boolean hasPeriod = false;
                while (between('0', '9') || (test('.') && !hasPeriod)) {
                    if (test('.')) {
                        hasPeriod = true;
                    }
                    number.append(take());
                }
                return new Const<>(type.parseType(number.toString()), type);
            } else if (test('(')) {
                return new Negate<>(primitive("There is no expression after '-'", type), true);
            } else {
                return new Negate<>(primitive("There is no expression after '-'", type));
            }
        }
        char copyCh = ch;
        for (String token : notPrimitive) {
            if (expect(token)) {
                throw new IllegalArgumentException("Unexpected character: " + copyCh + ". " + errorMessage);
            }
        }
        if (Character.isDigit(ch))
            throw new IllegalArgumentException("Unexpected character: " + copyCh + ". " + errorMessage);
        throw new IllegalArgumentException("Unexpected character: " + copyCh + ". Incorrect variable name.");
    }

    @Override
    public TripleExpression<T> parse(String expression, Type<T> type) throws Exception {
        exceptionParse(new StringSource(expression));
        TripleExpression<T> result = priority1("", type);
        if (!eof()) {
            throw new IllegalArgumentException("Unexpected character: " + ch + ". Invalid expression");
        }
        return result;
    }
}
