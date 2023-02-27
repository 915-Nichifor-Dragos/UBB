package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type {

    public String toString() {
        return "bool";
    }

    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public Value defaultValue()
    {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

}
