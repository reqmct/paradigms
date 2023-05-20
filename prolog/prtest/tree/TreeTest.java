package prtest.tree;

import base.Selector;
import base.TestCounter;
import prtest.map.MapChecker;

import java.util.Arrays;
import java.util.function.Consumer;
    
/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class TreeTest {
    public static final Selector SELECTOR = new Selector(TreeTest.class, "easy", "hard")
            .variant("base", variant(tests -> {}))
            ;

    private TreeTest() {
    }



    @SafeVarargs
    /* package-private */ static Consumer<TestCounter> variant(final Consumer<MapChecker<Void>>... addTests) {
        return variant(false, addTests);
    }

    @SafeVarargs
    /* package-private */ static Consumer<TestCounter> variant(final boolean alwaysUpdate, final Consumer<MapChecker<Void>>... addTests) {
        return counter -> {
            final boolean hard = counter.mode() == 1;
            TreeTester.test(
                    counter, hard || alwaysUpdate, true,
                    tests -> {
                        if (!hard) {
                            tests.clearUpdaters();
                        }
                        Arrays.stream(addTests).forEachOrdered(adder -> adder.accept(tests));
                    }
            );
        };
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
