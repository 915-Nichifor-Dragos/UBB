package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Type.Type;

public class ReturnStmt implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws ADTException {
        state.getSymbolTable().pop();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReturnStmt();
    }

    @Override
    public String toString() {
        return "return";
    }
}
