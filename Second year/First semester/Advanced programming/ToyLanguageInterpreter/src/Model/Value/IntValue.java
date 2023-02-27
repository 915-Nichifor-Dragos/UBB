package Model.Value;

import Model.Type.Type;
import Model.Type.IntType;

public class IntValue implements Value {
    int val;

    public String toString() {
        return "" + val;
    }

    public IntValue(int v) {
        val = v;
    }

    public IntValue() {}

    public int getVal() {
        return val;
    }

    public Type getType() {
        return new IntType();
    }

    public int add(int v) {
        return val + v;
    }

    public int subtract(int v) {
        return val - v;
    }

    public int multiply(int v) {
        return val * v;
    }

    public int divide(int v) {
        return val / v;
    }

}
