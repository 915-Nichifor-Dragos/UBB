package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Expression.RelationalExp;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class SwitchStmt implements IStmt {

    IExp exp, exp1, exp2;
    IStmt stmt1, stmt2, stmt3;

    public SwitchStmt(IExp exp, IExp exp1, IStmt stmt1, IExp exp2, IStmt stmt2, IStmt stmt3)
    {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public String toString() {
        return String.format("switch " + exp + " (case 1: " + exp1 + "), " + stmt1 + " (case 2: " + exp2 + ") " + stmt2 + ", default " + stmt3);
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        MyIStack<IStmt> stk = state.getStack();
        stk.push(new IfStmt(new RelationalExp("==", exp1, exp2), stmt1, new IfStmt(new RelationalExp("==", exp, exp2), stmt2, stmt3)));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (! exp.typeCheck(typeEnv).equals(exp1.typeCheck(typeEnv)) || ! exp.typeCheck(typeEnv).equals(exp2.typeCheck(typeEnv)))
            throw new ExpressionException("Types are not equal.");
        stmt1.typeCheck(typeEnv);
        stmt2.typeCheck(typeEnv);
        stmt3.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(), exp2.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }
}
