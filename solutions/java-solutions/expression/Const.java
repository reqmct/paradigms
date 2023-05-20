package expression;

import java.util.Objects;

public class Const implements Primitive {
    private int value;
    private double doubleValue;

    private final boolean isDouble;


    public Const(final int value) {
        this.value = value;
        isDouble = false;
    }

    public Const(final double doubleValue) {
        this.doubleValue = doubleValue;
        isDouble = true;
    }


    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public String toMiniString() {
        return this.toString();
    }

    @Override
    public String toString() {
        if (isDouble) {
            return Double.toString(doubleValue);
        }
        return Integer.toString(value);
    }

    @Override
    public int hashCode() {
        if (isDouble)
            return Double.hashCode(doubleValue);
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            if (((Const) obj).isDouble == this.isDouble) {
                if (!isDouble && ((Const) obj).value == this.value) {
                    return true;
                }
                return isDouble && ((Const) obj).doubleValue == this.doubleValue;
            }
        }
        return false;
    }

}
