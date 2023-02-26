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
    IExp exp;

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    public AssignStmt(String id, IExp exp)
    {
        this.id = id;
        this.exp = exp;
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, heap);
            Type typId = (symTbl.lookUp(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new StatementException("declared type of variable" + id + " and type of  the assigned expression do not match");
        } else
            throw new StatementException("the used variable" + id + " was not declared before");

        return state;
    }
}
