package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;
import Model.Expression.IExp;
import Model.Type.Type;

public interface IStmt {

    PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException;

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException;

    IStmt deepCopy();
    public String toString();

}
