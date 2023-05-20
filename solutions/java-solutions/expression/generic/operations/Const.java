package expression.generic.operations;


import expression.generic.types.Type;

public class Const<T> implements Primitive<T> {
    private final T value;
    private final Type<T> type;


    public Const(T value, Type<T> type) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public String toMiniString() {
        return value.toString();
    }

    @Override
    public Type<T> getType() {
        return type;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return value;
    }
}
