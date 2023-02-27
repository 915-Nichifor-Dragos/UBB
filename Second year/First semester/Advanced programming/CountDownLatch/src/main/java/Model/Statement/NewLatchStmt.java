package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt {

    String var;
    IExp exp;
    private static final Lock lock = new ReentrantLock();

    public NewLatchStmt(String var, IExp exp)
    {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {return String.format("newLatch(%s, %s)", var, exp);}

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        lock.lock();
        MyIDictionary<String, Value> symTbl = state.getSymbolTable();
        MyILatchTable latchTable = state.getLatchTable();
        MyIHeap heap = state.getHeap();
        if(! (exp.eval(symTbl, heap)).getType().equals(new IntType()))
            throw new StatementException("Value of the expression is not of int type");
        int result = ((IntValue) exp.eval(symTbl, heap)).getVal();
        int freeLocation = latchTable.getFreeAddress();
        latchTable.put(freeLocation, result);
        if (!symTbl.isDefined(var) || !(symTbl.lookUp(var)).getType().equals(new IntType()))
            throw new StatementException(String.format("Var %s is not of type int or it is not defined", var));
        symTbl.update(var, new IntValue(freeLocation));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (typeEnv.lookUp(var).equals(new IntType())) {
            if (exp.typeCheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            } else {
                throw new ExpressionException("Expression doesn't have the int type!");
            }
        } else {
            throw new StatementException(String.format("%s is not of int type!", var));
        }
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(var, exp.deepCopy());
    }
}
