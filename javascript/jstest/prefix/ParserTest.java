package jstest.prefix;

import base.Selector;

import static jstest.expression.Operations.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class ParserTest {
    public static final Selector SELECTOR = Kind.selector(
                    ParserTest.class,
                    "prefix", "parsePrefix", ParserTester.PREFIX
            )
            .variant("Base", ARITH)
            .variant("FloorCeil", FLOOR, CEIL)
            .variant("SumexpLSE", SUMEXP, LSE)
            .variant("MeansqRMS", MEANSQ, RMS)
            .variant("SumAvg", SUM, AVG)
            .selector();

    private ParserTest() {
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
