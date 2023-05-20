package expression.parser;

import java.util.List;

public class TokenArrayParser {
    private final List<Token> list;
    private int ind = 0;

    public TokenArrayParser(List<Token> list) {
        this.list = list;
    }


    public void goNext() {
        ind += 1;
    }
    public void goBack() {
        ind -= 1;
    }
    public Token getToken() {
        return list.get(ind);
    }
}
