package Model.Statement;

import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;

public interface IStmt {

    PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException;


}
