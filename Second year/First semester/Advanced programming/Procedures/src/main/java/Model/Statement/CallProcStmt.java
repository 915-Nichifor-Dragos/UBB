package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.Type;
import Model.Value.Value;

import java.util.List;

public class CallProcStmt implements IStmt {
    private final String procedureName;
    private final List<IExp> expressions;

    public CallProcStmt(String procedureName, List<IExp> expressions) {
        this.procedureName = procedureName;
        this.expressions = expressions;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException {
        MyIDictionary<String, Value> symTable = state.getTopSymTable();
        MyIHeap heap = state.getHeap();
        MyIProcTable procTable = state.getProcTable();
        if (procTable.isDefined(procedureName)) {
            List<String> variables = procTable.lookUp(procedureName).getKey();
            IStmt statement = procTable.lookUp(procedureName).getValue();
            MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
            for(String var: variables) {
                int ind = variables.indexOf(var);
                newSymTable.put(var, expressions.get(ind).eval(symTable, heap));
            }
            state.getSymbolTable().push(newSymTable);
            state.getStack().push(new ReturnStmt());
            state.getStack().push(statement);
        } else {
            throw new ADTException("Procedure not defined!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CallProcStmt(procedureName, expressions);
    }

    @Override
    public String toString() {
        return String.format("call %s%s", procedureName, expressions.toString());
    }
}
