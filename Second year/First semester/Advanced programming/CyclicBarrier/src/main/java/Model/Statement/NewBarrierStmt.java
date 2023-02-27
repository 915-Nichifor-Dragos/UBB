package Model.Statement;

import Model.ADTs.MyIBarrierTable;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
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

public class NewBarrierStmt implements IStmt {

    String var;
    IExp exp;
    private static final Lock lock = new ReentrantLock();

    public NewBarrierStmt(String var, IExp exp)
    {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {return String.format("newBarrier(%s, %s)", var, exp);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap heap = state.getHeap();
        MyIBarrierTable barrierTable = state.getBarrierTable();
        int nr = ((IntValue) exp.eval(symTable, heap)).getVal();
        int freeAddress = barrierTable.getFreeAddress();
        barrierTable.put(freeAddress, new Pair<>(nr, new ArrayList<>()));
        if (symTable.isDefined(var) && symTable.lookUp(var).getType().equals(new IntType()))
            symTable.update(var, new IntValue(freeAddress));
        else
            throw new ADTException(String.format("%s is not defined in the symbol table or it is not of type int!", var));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (typeEnv.lookUp(var).equals(new IntType()))
            if (exp.typeCheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new StatementException("Expression is not of type int!");
        else
            throw new StatementException("Variable is not of type int!");
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var, exp.deepCopy());
    }
}
