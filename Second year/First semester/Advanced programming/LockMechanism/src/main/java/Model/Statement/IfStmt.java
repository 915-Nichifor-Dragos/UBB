package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;
import Model.Expression.IExp;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

import java.util.Map;

public class IfStmt implements IStmt {

    IExp expression;
    IStmt thenStatement;
    IStmt elseStatement;

    @Override
    public String toString() {
        return "(IF(" + expression.toString() + ") THEN(" + thenStatement.toString() + ")ELSE(" + elseStatement.toString() + "))";
    }

    public IfStmt(IExp e, IStmt t, IStmt el) {
        expression = e;
        thenStatement = t;
        elseStatement = el;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Value> symTbl = state.getSymbolTable();
        MyIHeap heap = state.getHeap();

        BoolValue val = (BoolValue) expression.eval(symTbl, heap);
        if (val.getVal())
            thenStatement.execute(state);
        else
            elseStatement.execute(state);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            MyIDictionary <String, Type> typeEnv2 = new MyDictionary<>();
            for (Map.Entry<String, Type> entry: typeEnv.getContent().entrySet()) {
                typeEnv2.put(entry.getKey(), entry.getValue());
            }
            MyIDictionary <String, Type> typeEnv3 = new MyDictionary<>();
            for (Map.Entry<String, Type> entry: typeEnv.getContent().entrySet()) {
                typeEnv3.put(entry.getKey(), entry.getValue());
            }
            thenStatement.typeCheck(typeEnv2);
            elseStatement.typeCheck(typeEnv3);
            return typeEnv;
        } else
            throw new StatementException("The condition of IF does not have the type Bool.");
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

}
