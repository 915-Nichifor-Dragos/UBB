package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Expression.LogicExp;
import Model.Expression.NotExp;
import Model.Expression.ValueExp;
import Model.Type.Type;

public class RepeatUntilStmt implements IStmt {

    IStmt stmt;
    IExp exp;

    public RepeatUntilStmt(IStmt stmt, IExp exp)
    {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "repeat " + stmt + " until " + exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        MyIStack<IStmt> stk = state.getStack();
        stk.push(new CompStmt(stmt, new WhileStmt(new NotExp(exp), stmt)));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if ( !exp.typeCheck(typeEnv).toString().equals("bool"))
            throw new ExpressionException("Not of type bool");
        stmt.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(stmt.deepCopy(), exp.deepCopy());
    }
}
