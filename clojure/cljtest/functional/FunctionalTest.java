package cljtest.functional;

import base.Selector;

import static jstest.expression.Operations.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class FunctionalTest {
    public static final Selector SELECTOR = FunctionalTester.builder()
            .variant("Base",            NARY_ARITH)
            .variant("ExpLn",           EXP,        LN)
            .variant("ArcTan",          ATAN,       ATAN2)
            .variant("SumexpLSE",       SUMEXP,     LSE)
            .variant("MeansqRMS",       MEANSQ,     RMS)
            .selector();

    private FunctionalTest() {
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
