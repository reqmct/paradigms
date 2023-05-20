package expression.exceptions;

import expression.*;

import java.util.Arrays;
import java.util.List;


public class ExpressionParser extends BaseParser implements TripleParser {

    private final List<String> notPrimitive = Arrays.asList("-", "+", "log10", "pow10", "clear", "set", "/", "*", ")", "\0");

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
    private AllExpression priority1(String errorMessage) {
        skipSpace();
        AllExpression expression = priority2(errorMessage + "There is no left argument. ");
        skipSpace();
        while (test('c') || test('s')) {
            if (expect("clear")) {
                isCorrectOperation("clear");
                expression = new Clear(expression, priority2("There is no right argument. "));
            } else if (expect("set")) {
                isCorrectOperation("set");
                expression = new Set(expression, priority2("There is no right argument. "));
            }
            skipSpace();
        }
        return expression;
    }

    private AllExpression priority2(final String errorMessage) {
        skipSpace();
        AllExpression expression = priority3(errorMessage);
        skipSpace();
        while (test('+') || test('-')) {
            if (expect("+")) {
                expression = new CheckedAdd(expression, priority3("There is no right argument. "));
            } else if (expect("-")) {
                expression = new CheckedSubtract(expression, priority3("There is no right argument. "));
            }
            skipSpace();
        }
        return expression;
    }

    private AllExpression priority3(final String errorMessage) {
        skipSpace();
        AllExpression expression = primitive(errorMessage);
        skipSpace();
        while (test('*') || test('/')) {
            if (expect("*")) {
                expression = new CheckedMultiply(expression, primitive("There is no right argument. "));
            } else if (expect("/")) {
                expression = new CheckedDivide(expression, primitive("There is no right argument. "));
            }
            skipSpace();
        }
        return expression;
    }

    private AllExpression primitive(String errorMessage) {
        skipSpace();
        if (test('x') || test('y') || test('z')) {
            return new Variable(take());
        }
        if (between('0', '9')) {
            StringBuilder number = new StringBuilder();
            while (between('0', '9')) {
                number.append(take());
            }
            return new Const(Integer.parseInt(number.toString()));
        }
        if (expect("pow10")) {
            isCorrectOperation("pow10");
            return new Pow10(primitive("Incorrect argument of pow10. "));
        }
        if (expect("log10")) {
            isCorrectOperation("log10");
            return new Log10(primitive("Incorrect argument of log10. "));
        }
        if (expect("count")) {
            isCorrectOperation("count");
            return new Count(primitive("Incorrect argument of count. "));
        }
        if (take('(')) {
            AllExpression expression = priority1("Incorrect expression in parentheses. ");
            if (!test(')')) {
                throw new IllegalArgumentException("Unexpected character: " + take()
                        + ". There is no right bracket ");
            }
            take();
            return expression;
        }
        if (take('-')) {
            if (between('0', '9')) {
                StringBuilder number = new StringBuilder("-");
                while (between('0', '9')) {
                    number.append(take());
                }
                return new Const(Integer.parseInt(number.toString()));
            } else if (test('(')) {
                return new CheckedNegate(primitive("There is no expression after '-'"), true);
            } else {
                return new CheckedNegate(primitive("There is no expression after '-'"));
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
    public TripleExpression parse(String expression) {
        exceptionParse(new StringSource(expression));
        TripleExpression result = priority1("");
        if (!eof()) {
            throw new IllegalArgumentException("Unexpected character: " + ch + ". Invalid expression");
        }
        return result;
    }
}
