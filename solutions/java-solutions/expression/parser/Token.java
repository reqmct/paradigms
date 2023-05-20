package expression.parser;

public class Token {
    private final TokenType type;
    private final String value;
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
    public Token(TokenType type, char value) {
        this.type = type;
        this.value = Character.toString(value);
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
