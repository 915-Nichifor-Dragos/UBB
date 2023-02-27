package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Type.RefType;
import Model.Type.Type;
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

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else
            throw new ExpressionException("The rH argument is not a RefType.");
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(expression.deepCopy());
    }

}
