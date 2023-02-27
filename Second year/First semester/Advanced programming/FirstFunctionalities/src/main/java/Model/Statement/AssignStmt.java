package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;
import Model.Expression.IExp;
import Model.ADTs.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt {

    String id;
    IExp expression;

    @Override
    public String toString() {
        return id + "=" + expression.toString();
    }

    public AssignStmt(String id, IExp exp)
    {
        this.id = id;
        this.expression = exp;
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymbolTable();
        MyIHeap heap = state.getHeap();

        if (symTbl.isDefined(id)) {
            Value val = expression.eval(symTbl, heap);
            Type typId = (symTbl.lookUp(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new StatementException("declared type of variable" + id + " and type of  the assigned expression do not match");
        } else
            throw new StatementException("the used variable" + id + " was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        Type typeVar = typeEnv.lookUp(id);
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExpr))
            return typeEnv;
        else
            throw new StatementException("Assignment: right hand side and left hand side have different types.");
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, expression.deepCopy());
    }

}
