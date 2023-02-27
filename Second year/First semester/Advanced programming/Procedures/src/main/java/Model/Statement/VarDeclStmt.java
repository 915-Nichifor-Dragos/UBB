package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Type.Type;
import Model.Value.Value;

public class VarDeclStmt implements IStmt {

    String name;
    Type type;

    @Override
    public String toString()
    {
        return type.toString() + " " + name;
    }

    public VarDeclStmt(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException {

        MyIDictionary<String, Value> symTbl = state.getSymbolTable().peek();
        symTbl.put(name, type.defaultValue());

        return null;

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type);
    }

}
