package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }

    public RefValue(int addr, Type type)
    {
        address = addr;
        locationType = type;
    }

    public int getAddr() {
        return address;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }

}