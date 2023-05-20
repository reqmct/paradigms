package expression;

import java.util.Objects;

public class Variable implements Primitive {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public Variable(char name) {
        this.name = Character.toString(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (getName()) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0;
        };
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            return Objects.equals(this.getName(), ((Variable) obj).getName());
        }
        return false;
    }
}
