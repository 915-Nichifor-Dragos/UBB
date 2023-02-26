package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class LogicExp implements IExp {

    IExp e1;
    IExp e2;

    int op; // 1-and,2-or

    public LogicExp(IExp e1, IExp e2, int op)
    {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString()
    {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionException, ADTException {

        Value v1 = e1.eval(tbl, heap);
        Value v2 = e2.eval(tbl, heap);

        if (v1.getType().equals(new IntType())) {
            IntValue i1 = (IntValue) v1;

            if (i1.getVal() == 0)
                return new BoolValue(false);

            if (v2.getType().equals(new IntType())) {
                IntValue i2 = (IntValue) v2;
                if (i2.getVal() != 0) {
                    return new BoolValue(true);
                } else {
                    return new BoolValue(false);
                }
            }

            if (v2.getType().equals(new BoolType())) {
                BoolValue b2 = (BoolValue) v2;
                return new BoolValue(b2.getVal());
            }
        }

        if (v1.getType().equals(new BoolType())) {
            BoolValue b1 = (BoolValue) v1;

            if (!b1.getVal())
                return new BoolValue(b1.getVal());

            if (v2.getType().equals(new IntType())) {
                IntValue i2 = (IntValue) v2;
                if (i2.getVal() != 0) {
                    return new BoolValue(true);
                } else {
                    return new BoolValue(false);
                }
            }

            if (v2.getType().equals(new BoolType())) {
                BoolValue b2 = (BoolValue) v2;
                return new BoolValue(b2.getVal());
            }
        }

        return null;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new ExpressionException("Second operand is not a boolean.");
        } else
            throw new ExpressionException("First operand is not a boolean.");
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }

}

