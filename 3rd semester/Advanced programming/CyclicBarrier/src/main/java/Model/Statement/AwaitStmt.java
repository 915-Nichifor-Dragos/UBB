package Model.Statement;

import Model.ADTs.MyIBarrierTable;
import Model.ADTs.MyIDictionary;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt{

    String var;
    private static final Lock lock = new ReentrantLock();

    public AwaitStmt(String var)
    {
        this.var = var;
    }

    @Override
    public String toString() {return String.format("(await %s)", var);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIBarrierTable barrierTable = state.getBarrierTable();
        if (symTable.isDefined(var) && symTable.lookUp(var).getType().equals(new IntType())) {
            int foundIndex = ((IntValue)symTable.lookUp(var)).getVal();
            if (barrierTable.containsKey(foundIndex)) {
                Pair<Integer, List<Integer>> foundBarrier = barrierTable.get(foundIndex);
                int NL = foundBarrier.getValue().size();
                int N1 = foundBarrier.getKey();
                ArrayList<Integer> list = (ArrayList<Integer>) foundBarrier.getValue();
                if (N1 > NL) {
                    if (list.contains(state.getId()))
                        state.getStack().push(this);
                    else {
                        list.add(state.getId());
                        state.getStack().push(this);
                        barrierTable.update(foundIndex, new Pair<>(N1, list));
                    }
                }
            } else {
                throw new StatementException("Index not in Barrier Table!");
            }
        } else {
            throw new StatementException("Var is not defined or is not if type int!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (typeEnv.lookUp(var).equals(new IntType()))
            return typeEnv;
        else
            throw new StatementException("Var is not of type int!");
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }
}
