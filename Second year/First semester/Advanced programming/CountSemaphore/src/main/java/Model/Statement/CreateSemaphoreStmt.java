package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyISemaphoreTable;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStmt implements IStmt {

    String var;
    IExp exp;
    private static final Lock lock = new ReentrantLock();

    public CreateSemaphoreStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {return String.format("createSemaphore(%s, %s)", var, exp);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();
        MyIHeap heap = state.getHeap();
        if (! exp.eval(symTable, heap).getType().equals(new IntType()))
            throw new StatementException(String.format("Expression %s is not of type int", exp));
        int result = ((IntValue)exp.eval(symTable, heap)).getVal();
        int freeLocation = semaphoreTable.getFreeAddress();
        semaphoreTable.put(freeLocation, new Pair<>(result, new ArrayList<>()));
        if (!symTable.isDefined(var) || !symTable.lookUp(var).getType().equals(new IntType()))
            throw new StatementException(String.format("Var %s is not of type int or is not defined", var));
        symTable.update(var, new IntValue(freeLocation));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (! typeEnv.lookUp(var).equals(new IntType()))
            throw new StatementException("Var must be of int type");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(var, exp.deepCopy());
    }
}
