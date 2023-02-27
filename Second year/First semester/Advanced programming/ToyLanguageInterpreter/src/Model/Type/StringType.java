package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{

    public String toString() {
        return "string";
    }

    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue()
    {
        return new StringValue("");
    }
}
