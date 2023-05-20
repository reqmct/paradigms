package expression.exceptions;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BaseParser {
    private static final char END = '\0';
    private CharSource source;
    protected char ch = 0xffff;

    public void exceptionParse(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }


    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean expect(final char expected) {
        if (!take(expected)) {
            return false;
        }
        return true;
    }

    protected boolean expect(final String value) {
        int copyPos = source.getPos();
        char copyCh = ch;
        for (final char c : value.toCharArray()) {
            if (!expect(c)) {
                ch = copyCh;
                source.setPos(copyPos);
                return false;
            }
        }
        return true;
    }


    protected boolean eof() {
        return take(END);
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}
