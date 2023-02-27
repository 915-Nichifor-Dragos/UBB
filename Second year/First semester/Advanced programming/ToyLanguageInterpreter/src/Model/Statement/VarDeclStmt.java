package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

public class VarDeclStmt implements IStmt {

    String name;
    Type typ;

    @Override
    public String toString()
    {
        return typ.toString() + " " + name;
    }

    public VarDeclStmt(String name, Type typ)
    {
        this.name = name;
        this.typ = typ;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException {

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        symTbl.put(name, typ.defaultValue());

        return state;

    }

}
