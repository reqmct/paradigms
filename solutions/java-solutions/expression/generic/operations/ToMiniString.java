package expression.generic.operations;

public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }
}

