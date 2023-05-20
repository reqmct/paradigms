package expression.generic.types;

import java.math.BigInteger;

public abstract class NumberType<T extends Number> implements Type<T> {
    @Override
    public T negate(T x) {
        return subtract(getZero(), x);
    }

    @Override
    public T square(T x) {
        return multiply(x, x);
    }
}
