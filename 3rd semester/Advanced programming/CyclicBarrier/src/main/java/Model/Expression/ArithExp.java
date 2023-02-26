package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements IExp {

    IExp e1;
    IExp e2;

    int op;  // 1-plus, 2-minus, 3-multiply, 4-divide

    @Override
    public String toString() {
        if (op == 1)
            return e1.toString() + " + " + e2.toString();
        if (op == 2)
            return e1.toString() + " - " + e2.toString();
        if (op == 3)
            return e1.toString() + " * " + e2.toString();
        if (op == 4)
            return e1.toString() + " / " + e2.toString();
        throw new ArithmeticException("No operator!");
    }

    public ArithExp(int op, IExp e1, IExp e2) {

        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionException, ADTException {

        Value v1, v2;

        v1 = e1.eval(tbl, heap);

        if (v1.getType().equals(new IntType())) {

            v2 = e2.eval(tbl, heap);

            if (v2.getType().equals(new IntType())) {

                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n;
                n = i2.getVal();
                if (op == 1)
                    return new IntValue(i1.add(n));
                if (op == 2)
                    return new IntValue(i1.subtract(n));
                if (op == 3)
                    return new IntValue(i1.multiply(n));
                if (op == 4)
                    if (n == 0)
                        throw new ExpressionException("Division by zero");
                    else
                        return new IntValue(i1.divide(n));

            } else throw new ExpressionException("Second operand is not an integer");

        } else throw new ExpressionException("First operand is not an integer");

        return null;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new ExpressionException("Second operand is not an integer.");
        } else
            throw new ExpressionException("First operand is not an integer.");
    }

    @Override
    public IExp deepCopy() {
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }

}
