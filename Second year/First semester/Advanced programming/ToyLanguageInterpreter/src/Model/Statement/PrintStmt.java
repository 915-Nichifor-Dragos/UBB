package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIList;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;
import Model.Expression.IExp;
import Model.ADTs.PrgState;
import Model.Value.Value;

public class PrintStmt implements IStmt {

    IExp exp;

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    public PrintStmt(IExp exp)
    {
        this.exp = exp;
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyIList<Value> out = state.getOut();

        out.add(exp.eval(symTbl, heap));

        return state;
    }

}