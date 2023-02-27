package Model.Value;

import Model.Type.Type;
import Model.Type.BoolType;


public class BoolValue implements Value {

    Boolean val;

    public String toString() {
        if (val == null)
            return "false";
        if (val)
            return "true";
        else return "false";
    }

    public BoolValue(Boolean v) {
        val = v;
    }

    public BoolValue() {}

    public Boolean getVal() {
        return val;
    }

    public Type getType() {
        return new BoolType();
    }

}
