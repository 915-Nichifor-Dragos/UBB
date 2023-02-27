package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;
import Model.Expression.IExp;
import Model.ADTs.PrgState;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IfStmt implements IStmt {

    IExp exp;
    IStmt thenS;
    IStmt elseS;

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    public IfStmt(IExp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {

        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap heap = state.getHeap();

        BoolValue val = (BoolValue) exp.eval(symTbl, heap);
        if (val.getVal())
            thenS.execute(state);
        else
            elseS.execute(state);

        return state;
    }

}
