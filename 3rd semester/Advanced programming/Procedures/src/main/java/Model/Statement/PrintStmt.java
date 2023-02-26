package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIList;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;
import Model.Expression.IExp;
import Model.ADTs.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt {

    IExp expression;

    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    public PrintStmt(IExp exp)
    {
        this.expression = exp;
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        MyIDictionary<String, Value> symTbl = state.getSymbolTable().peek();
        MyIHeap heap = state.getHeap();
        MyIList<Value> out = state.getOutTable();

        out.add(expression.eval(symTbl, heap));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expression.deepCopy());
    }

}