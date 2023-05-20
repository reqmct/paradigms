package expression.generic.types;

public interface Type<T> {
    T add(T x, T y);

    T subtract(T x, T y);

    T multiply(T x, T y);
    T divide (T x, T y);
    T negate(T x);
    T mod(T x, T y);
    T abs(T x);
    T square(T x);

    T getZero();

    T parseType(String source);
    T parseType(int x);

}
