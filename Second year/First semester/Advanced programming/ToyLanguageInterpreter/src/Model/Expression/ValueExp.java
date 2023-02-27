package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpressionException;
import Model.Value.Value;

public class ValueExp implements IExp {

    Value e;

    @Override
    public String toString() { return this.e.toString();}

    public ValueExp(Value e)
    {
        this.e = e;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionException {
        return e;
    }

}
