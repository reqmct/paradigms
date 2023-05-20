package expression.generic.parser;

import expression.generic.operations.*;
import expression.generic.types.Type;

public interface TripleParser<T> {
    TripleExpression<T> parse(String expression, Type<T> type) throws Exception;
}
