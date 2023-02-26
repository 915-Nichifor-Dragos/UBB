package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Type.Type;
import Model.Value.Value;

import java.util.Map;

public class ForkStmt implements IStmt {


    private final IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", statement.toString());
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        MyIStack<IStmt> newStack = new MyStack<>();
        newStack.push(statement);
        MyIStack<MyIDictionary<String, Value>> newSymTable = state.getSymbolTable().clone();
        return new PrgState(newStack, newSymTable, state.getOutTable(), state.getFileTable(), state.getHeap(), state.getProcTable());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

}
