package Model.Statement;

import Model.ADTs.PrgState;
import Model.Exceptions.StatementException;

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

        return state;
    }

}
