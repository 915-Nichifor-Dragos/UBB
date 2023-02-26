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
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        for (Map.Entry<String, Value> entry: state.getSymbolTable().getContent().entrySet()) {
            newSymTable.put(entry.getKey(), entry.getValue());
        }

        return new PrgState(newStack, newSymTable, state.getOutTable(), state.getFileTable(), state.getHeap());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        MyIDictionary <String, Type> typeEnv2 = new MyDictionary<>();
        for (Map.Entry<String, Type> entry: typeEnv.getContent().entrySet()) {
            typeEnv2.put(entry.getKey(), entry.getValue());
        }
        statement.typeCheck(typeEnv2);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

}
