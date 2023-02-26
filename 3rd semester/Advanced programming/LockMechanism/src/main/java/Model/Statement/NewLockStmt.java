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

public class NewLockStmt implements IStmt {

    String var;
    private static final Lock lock = new ReentrantLock();

    public NewLockStmt(String var) {this.var = var;}

    @Override
    public String toString() {return String.format("newLock(%s)", var);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyILockTable lockTable = state.getLockTable();
        int freeAddress = lockTable.getFreeValue();
        lockTable.put(freeAddress, -1);
        if (symTable.isDefined(var) && symTable.lookUp(var).getType().equals(new IntType()))
            symTable.update(var, new IntValue(freeAddress));
        else
            throw new StatementException("Var is not of type int");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (typeEnv.lookUp(var).equals(new IntType()))
            return typeEnv;
        else
            throw new StatementException("Var not of type int");
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }
}
