package expression.generic.operations;


import expression.generic.types.Type;

public class Variable<T> implements Primitive<T> {

    private final Type<T> type;
    private final String name;
    public Variable(String name, Type<T> type) {
        this.type = type;
        this.name = name;
    }

    public Variable(char name, Type<T> type) {
        this.type = type;
        this.name = Character.toString(name);
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toMiniString() {
        return name;
    }

    @Override
    public Type<T> getType() {
        return type;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> getType().getZero();
        };
    }
}
