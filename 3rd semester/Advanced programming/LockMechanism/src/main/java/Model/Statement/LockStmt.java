package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyILockTable;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStmt implements IStmt {

    String var;
    private static final Lock lock = new ReentrantLock();

    public LockStmt(String var) {this.var = var;}

    @Override
    public String toString() {return String.format("lock(%s)", var);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyILockTable lockTable = state.getLockTable();
        if (! symTable.isDefined(var) || ! symTable.lookUp(var).getType().equals(new IntType()))
            throw new StatementException("Var is not of type int or is not defined");
        int foundIndex = ((IntValue) symTable.lookUp(var)).getVal();
        if (! lockTable.keySet().contains(foundIndex))
            throw new StatementException("Index is not present in the lock table");
        if (lockTable.get(foundIndex) == -1)
            lockTable.update(foundIndex, state.getId());
        else
            state.getStack().push(this);
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (typeEnv.lookUp(var).equals(new IntType()))
            return typeEnv;
        else
            throw new StatementException("Var is not of type int");
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(var);
    }
}
