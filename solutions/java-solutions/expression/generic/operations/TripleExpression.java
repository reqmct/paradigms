package expression.generic.operations;

import expression.generic.types.Type;

public interface TripleExpression<T> extends ToMiniString{
    Type<T> getType();
    T evaluate(T x, T y, T z);
}
