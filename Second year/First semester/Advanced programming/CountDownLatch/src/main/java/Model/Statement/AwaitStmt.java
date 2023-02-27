package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {

    String var;
    private static final Lock lock = new ReentrantLock();
    public AwaitStmt(String var)
    {
        this.var = var;
    }

    @Override
    public String toString() {return String.format("await(%s)", var);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIStack<IStmt> stack = state.getStack();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyILatchTable latchTable = state.getLatchTable();
        if (!symTable.isDefined(var) || !symTable.lookUp(var).getType().equals(new IntType()))
            throw new StatementException(String.format("Var %s is not of type int or it is not defined", var));
        int foundIndex = ((IntValue)symTable.lookUp(var)).getVal();
        if (! latchTable.containsKey(foundIndex))
            throw new StatementException(String.format("%d is not an index in the latch table", foundIndex));
        if (latchTable.get(foundIndex) != 0)
            stack.push(this);
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (! typeEnv.lookUp(var).equals(new IntType()))
            throw new StatementException("Var is not of type int");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }
}
