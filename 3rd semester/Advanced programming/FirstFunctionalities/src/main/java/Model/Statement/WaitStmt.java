package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.ValueExp;
import Model.Type.Type;
import Model.Value.IntValue;

public class WaitStmt implements IStmt {

    Integer number;

    public WaitStmt(Integer number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "wait(" + number + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        MyIStack<IStmt> stk = state.getStack();
        if (number != 0)
            stk.push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(number))), new WaitStmt(number - 1)));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStmt(number);
    }
}
