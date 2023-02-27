package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExp implements IExp {

    private final IExp expression;

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", expression);
    }

    public ReadHeapExp(IExp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heap) throws ExpressionException, ADTException {
        Value value = expression.eval(symTable, heap);
        if (!(value instanceof RefValue))
            throw new ExpressionException(String.format("%s not of RefType", value));
        RefValue refValue = (RefValue) value;
        return heap.get(refValue.getAddr());
    }
}
