package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value {

    String val;

    public String toString() {return val;}

    public StringValue(String v) {
        val = v;
    }

    public StringValue() {}

    public String getVal() {
        return val;
    }

    public Type getType() {
        return new StringType();
    }

}
