package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Type.Type;

public class NopeStmt implements IStmt {

    String message;

    @Override
    public String toString() {
        return "(no operation)";
    }

    public NopeStmt (String message)
    {
        this.message = message;
    }

    public PrgState execute(PrgState state) throws StatementException {

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NopeStmt(message);
    }

}
