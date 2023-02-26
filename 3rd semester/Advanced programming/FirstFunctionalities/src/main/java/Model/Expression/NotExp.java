package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class NotExp implements IExp{

    IExp exp;

    public NotExp(IExp exp)
    {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return String.format("!(%s)", exp);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        return exp.typeCheck(typeEnv);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionException, ADTException {
        BoolValue value = (BoolValue) exp.eval(tbl, heap);
        if (!value.getVal())
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public IExp deepCopy() {
        return new NotExp(exp.deepCopy());
    }
}
