package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyISemaphoreTable;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements IStmt {

    String var;
    private static final Lock lock = new ReentrantLock();

    public ReleaseStmt(String var) {this.var = var;}

    @Override
    public String toString() {return String.format("release(%s)", var);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();
        if (!symTable.isDefined(var) || !symTable.lookUp(var).getType().equals(new IntType()))
            throw new StatementException(String.format("Var %s is not of type int or is not defined", var));
        int foundIndex = ((IntValue)symTable.lookUp(var)).getVal();
        if (!semaphoreTable.containsKey(foundIndex))
            throw new StatementException(String.format("The value %d is not an index in the semaphore table", foundIndex));
        Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(foundIndex);
        if (foundSemaphore.getValue().contains(state.getId())) {
            foundSemaphore.getValue().remove((Integer) state.getId());
            semaphoreTable.update(foundIndex, new Pair<>(foundSemaphore.getKey(), foundSemaphore.getValue()));
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (!typeEnv.lookUp(var).equals(new IntType()))
            throw new StatementException("Var is not of type int");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseStmt(var);
    }
}
