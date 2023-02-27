package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpressionException;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements IExp {

    Value value;

    @Override
    public String toString() { return this.value.toString();}

    public ValueExp(Value e)
    {
        this.value = e;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionException {
        return value;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return value.getType();
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(value);
    }

}
