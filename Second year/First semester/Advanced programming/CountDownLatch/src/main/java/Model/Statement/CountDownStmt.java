package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyILatchTable;
import Model.ADTs.MyIList;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.ValueExp;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt {
    String var;
    private static final Lock lock = new ReentrantLock();

    public CountDownStmt(String var)
    {
        this.var = var;
    }

    @Override
    public String toString() {return String.format("countDown(%s)", var);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyILatchTable latchTable = state.getLatchTable();
        if (!symTable.isDefined(var) || !symTable.lookUp(var).getType().equals(new IntType()))
            throw new StatementException(String.format("Var %s is not of type int or it is not defined", var));
        int foundIndex = ((IntValue)symTable.lookUp(var)).getVal();
        if(! latchTable.containsKey(foundIndex))
            throw new StatementException(String.format("%d is not an index in the latch table", foundIndex));
        int number = latchTable.get(foundIndex);
        if (number > 0)
            latchTable.update(foundIndex, number - 1);
        state.getStack().push(new PrintStmt(new ValueExp(new IntValue(state.getId()))));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (! typeEnv.lookUp(var).equals(new IntType()))
            throw new StatementException("Var not of type int");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStmt(var);
    }
}
