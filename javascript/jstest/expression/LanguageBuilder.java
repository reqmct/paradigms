package jstest.expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface LanguageBuilder {
    Variant getVariant();

    Language language(Dialect parsed, Dialect unparsed);
}
