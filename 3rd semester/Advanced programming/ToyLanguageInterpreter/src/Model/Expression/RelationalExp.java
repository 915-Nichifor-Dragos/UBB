package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Objects;

public class RelationalExp implements IExp {

    IExp exp1;
    IExp exp2;
    String operator;

    @Override
    public String toString() {
        return this.exp1.toString() + " " + this.operator + " " + this.exp2.toString();
    }

    public RelationalExp(String operator, IExp expression1, IExp expression2) {
        this.exp1 = expression1;
        this.exp2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heap) throws ExpressionException, ADTException {
        Value value1, value2;
        value1 = this.exp1.eval(symTable, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = this.exp2.eval(symTable, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue val1 = (IntValue) value1;
                IntValue val2 = (IntValue) value2;
                int v1, v2;
                v1 = val1.getVal();
                v2 = val2.getVal();
                if (Objects.equals(this.operator, "<"))
                    return new BoolValue(v1 < v2);
                else if (Objects.equals(this.operator, "<="))
                    return new BoolValue(v1 <= v2);
                else if (Objects.equals(this.operator, "=="))
                    return new BoolValue(v1 == v2);
                else if (Objects.equals(this.operator, "!="))
                    return new BoolValue(v1 != v2);
                else if (Objects.equals(this.operator, ">"))
                    return new BoolValue(v1 > v2);
                else if (Objects.equals(this.operator, ">="))
                    return new BoolValue(v1 >= v2);
            } else
                throw new ExpressionException("Second operand is not an integer.");
        } else
            throw new ExpressionException("First operand in not an integer.");
        return null;
    }

}
