package expression.exceptions;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface CharSource {
    boolean hasNext();
    char next();

    int getPos();
    void setPos(int pos);
    IllegalArgumentException error(String message);
}
