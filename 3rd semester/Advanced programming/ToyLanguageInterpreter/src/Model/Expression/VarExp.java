package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Value.Value;

public class VarExp implements IExp {

    String id;

    @Override
    public String toString() {
        return id;
    }

    public VarExp(String id)
    {
        this.id = id;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionException, ADTException {
        return tbl.lookUp(id);
    }

}
