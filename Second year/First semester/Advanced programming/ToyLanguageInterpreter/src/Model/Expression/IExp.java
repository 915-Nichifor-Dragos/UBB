package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Value.Value;

public interface IExp {

    Value eval(MyIDictionary<String,Value> tbl, MyIHeap heap) throws ExpressionException, ADTException;

}
