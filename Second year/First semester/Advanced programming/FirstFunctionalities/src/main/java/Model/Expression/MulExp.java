package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class MulExp implements IExp {

    IExp exp1, exp2;

    public MulExp(IExp exp1, IExp exp2)
    {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public String toString()
    {
        return "((" + exp1 + "*" + exp2 + ")-(" + exp1 + "*" + exp2 + "))";
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new ExpressionException("Second operand is not an integer.");
        } else
            throw new ExpressionException("First operand is not an integer.");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionException, ADTException {
        return new ArithExp(2, new ArithExp(3, exp1, exp2), new ArithExp(1, exp1, exp2)).eval(tbl, heap);
    }

    @Override
    public IExp deepCopy() {
        return new MulExp(exp1.deepCopy(), exp2.deepCopy());
    }
}
