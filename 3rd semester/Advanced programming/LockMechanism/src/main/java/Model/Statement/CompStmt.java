package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Type.Type;

public class CompStmt implements IStmt {

    IStmt first;
    IStmt second;

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public CompStmt(IStmt first, IStmt second)
    {
        this.first = first;
        this.second = second;
    }

    public PrgState execute(PrgState state) throws StatementException {

        MyIStack<IStmt> stk = state.getStack();
        stk.push(second);
        stk.push(first);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

}
