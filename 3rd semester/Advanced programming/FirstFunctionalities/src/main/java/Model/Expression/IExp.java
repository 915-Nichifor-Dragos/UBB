package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Type.Type;
import Model.Value.Value;

public interface IExp {

    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException;

    Value eval(MyIDictionary<String,Value> tbl, MyIHeap heap) throws ExpressionException, ADTException;
    IExp deepCopy();
    public String toString();

}
