package expression.generic.types;

import java.math.BigInteger;

public class BigIntegerType extends NumberType<BigInteger> {
    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    public BigInteger abs(BigInteger x) {
        return x.abs();
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) {
        return x.mod(y);
    }

    @Override
    public BigInteger getZero() {
        return BigInteger.ZERO;
    }

    @Override
    public BigInteger parseType(String source) {
        return new BigInteger(source);
    }

    @Override
    public BigInteger parseType(int x) {
        return parseType(Integer.toString(x));
    }
}
