package expression.generic.operations;

import expression.generic.types.Type;

public abstract class AbstractExpression <T> implements TripleExpression<T> {
    private final Type<T> type;
    protected AbstractExpression(Type<T>  type) {
        this.type = type;
    }

    @Override
    public Type<T> getType() {
        return type;
    }
}
