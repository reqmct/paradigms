package expression.generic.operations;

import expression.generic.types.Type;

public abstract class AbstractOperation<T> extends AbstractExpression<T> implements Operation<T> {
    protected AbstractOperation(Type<T> type) {
        super(type);
    }

    public abstract String getOperationSymbol();
}

