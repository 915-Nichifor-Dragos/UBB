package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Expression.RelationalExp;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.Value;

public class CondAssignStmt implements IStmt {

    String id;
    IExp exp;
    IExp exp1;
    IExp exp2;

    @Override
    public String toString() {
        return String.format(id + "=" + exp + "?" + exp1 + ":" + exp2);
    }

    public CondAssignStmt(String id, IExp exp, IExp exp1, IExp exp2)
    {
        this.id = id;
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        MyIStack<IStmt> stk = state.getStack();
        stk.push(new IfStmt(exp, new AssignStmt(id, exp1), new AssignStmt(id, exp2)));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        Type typeExpr = exp.typeCheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            Type typeVar = typeEnv.lookUp(id);
            if (! typeVar.equals(exp1.typeCheck(typeEnv)) || ! typeVar.equals(exp2.typeCheck(typeEnv)))
                throw new ExpressionException("Types are not equal.");
        }
        else throw new ExpressionException("Expression not bool type");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CondAssignStmt(id, exp.deepCopy(), exp1.deepCopy(), exp2.deepCopy());
    }
}
