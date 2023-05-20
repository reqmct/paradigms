package expression.parser;

import expression.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressionParser implements TripleParser {
    private final Map<String, TokenType> convertToken = Map.of(
            "(", TokenType.LP,
            ")", TokenType.RP,
            "+", TokenType.PLUS,
            "*", TokenType.MUL,
            "/", TokenType.DIV,
            "set", TokenType.SET,
            "count", TokenType.COUNT,
            "clear", TokenType.CLEAR
    );

    private List<Token> getTokenArray(String expression) {
        List<Token> tokenArray = new ArrayList<>();
        int ind = 0;
        while (ind < expression.length()) {
            char c = expression.charAt(ind);
            if (Character.isWhitespace(c)) {
                ind++;
                continue;
            }
            if (convertToken.containsKey(Character.toString(c))) {
                tokenArray.add(new Token(convertToken.get(Character.toString(c)), Character.toString(c)));
                ind++;
                continue;
            }
            if (c == '-') {
                char nextC = expression.charAt(ind + 1);
                if (!Character.isDigit(nextC)) {
                    tokenArray.add(new Token(TokenType.MINUS, c));
                    ind++;
                    continue;
                } else if (!tokenArray.isEmpty()) {
                    switch (tokenArray.get(tokenArray.size() - 1).getType()) {
                        case NAME, NUMBER, RP -> {
                            tokenArray.add(new Token(TokenType.MINUS, c));
                            ind++;
                            continue;
                        }
                    }
                }
            }
            StringBuilder currant = new StringBuilder();
            boolean isNumber = Character.isDigit(c) || c == '-';
            currant.append(c);
            ind++;
            while (ind < expression.length()) {
                c = expression.charAt(ind);
                if (!(Character.isLetter(c) || Character.isDigit(c))) {
                    break;
                }
                if (Character.isLetter(c)) {
                    isNumber = false;
                }
                ind++;
                currant.append(c);
            }
            if (isNumber) {
                tokenArray.add(new Token(TokenType.NUMBER, currant.toString()));
            } else if (convertToken.containsKey(currant.toString())) {
                tokenArray.add(new Token(convertToken.get(currant.toString()), currant.toString()));
            } else {
                tokenArray.add(new Token(TokenType.NAME, currant.toString()));
            }
        }
        tokenArray.add(new Token(TokenType.END_EXP, ""));
        return tokenArray;
    }

    private AllExpression parsePrim(TokenArrayParser tokens) {
        Token token = tokens.getToken();
        switch (token.getType()) {
            case NAME -> {
                return new Variable(token.getValue());
            }
            case NUMBER -> {
                return new Const(Integer.parseInt(token.getValue()));
            }
            case MINUS -> {
                tokens.goNext();
                Token nextToken = tokens.getToken();
                tokens.goNext();
                switch (nextToken.getType()) {
                    case LP -> {
                        AllExpression expression = parseExpPriority0(tokens);
                        tokens.goNext();
                        return new UnarySubstract(expression, true);
                    }
                    case MINUS, COUNT -> {
                        tokens.goBack();
                        return new UnarySubstract(parsePrim(tokens));
                    }
                    case NUMBER -> {
                        return new UnarySubstract(new Const(Integer.parseInt(nextToken.getValue())));
                    }
                    case NAME -> {
                        return new UnarySubstract(new Variable(nextToken.getValue()));
                    }
                }
                return null;
            }
            case LP -> {
                tokens.goNext();
                AllExpression expression = parseExpPriority0(tokens);
                tokens.goNext();
                return expression;
            }
            case COUNT -> {
                tokens.goNext();
                return new Count(parsePrim(tokens));
            }
        }
        return null;
    }

    private AllExpression parseExpPriority0(TokenArrayParser tokens) {
        AllExpression expression = parseExpPriority1(tokens);
        while (true) {
            Token token = tokens.getToken();
            tokens.goNext();
            switch (token.getType()) {
                case SET -> expression = new Set(expression, parseExpPriority1(tokens));
                case CLEAR -> expression = new Clear(expression, parseExpPriority1(tokens));
                case RP, END_EXP -> {
                    tokens.goBack();
                    return expression;
                }
            }
        }
    }

    private AllExpression parseExpPriority1(TokenArrayParser tokens) {
        AllExpression expression = parseExpPriority2(tokens);
        while (true) {
            Token token = tokens.getToken();
            tokens.goNext();
            switch (token.getType()) {
                case PLUS -> expression = new Add(expression, parseExpPriority2(tokens));
                case MINUS -> expression = new Subtract(expression, parseExpPriority2(tokens));
                case RP, END_EXP, SET, CLEAR -> {
                    tokens.goBack();
                    return expression;
                }
            }
        }
    }

    private AllExpression parseExpPriority2(TokenArrayParser tokens) {
        AllExpression expression = parsePrim(tokens);
        while (true) {
            Token token = tokens.getToken();
            tokens.goNext();
            switch (token.getType()) {
                case MUL -> expression = new Multiply(expression, parsePrim(tokens));
                case DIV -> expression = new Divide(expression, parsePrim(tokens));
                case RP, PLUS, MINUS, END_EXP, SET, CLEAR -> {
                    tokens.goBack();
                    return expression;
                }
            }
        }
    }

    @Override
    public TripleExpression parse(String expression) {
        List<Token> tokenArray = getTokenArray(expression);
        TokenArrayParser tokens = new TokenArrayParser(tokenArray);
        return parseExpPriority0(tokens);
    }
}
