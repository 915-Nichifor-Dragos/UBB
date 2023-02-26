package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type {

    public String toString() {
        return "int";
    }

    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public Value defaultValue()
    {
        return new IntValue(0);
    }
}
