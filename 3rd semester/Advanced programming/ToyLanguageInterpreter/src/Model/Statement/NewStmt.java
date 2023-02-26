package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class NewStmt implements IStmt {

    String varName;
    IExp expression;

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }

    public NewStmt(String name, IExp exp)
    {
        varName = name;
        expression = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (!symTable.isDefined(varName))
            throw new StatementException(String.format("%s not in symTable", varName));
        Value varValue = symTable.lookUp(varName);
        if (!(varValue.getType() instanceof RefType))
            throw new StatementException(String.format("%s in not of RefType", varName));
        Value evaluated = expression.eval(symTable, heap);
        Type locationType = ((RefValue)varValue).getLocationType();
        if (!locationType.equals(evaluated.getType()))
            throw new StatementException(String.format("%s not of %s", varName, evaluated.getType()));
        int newPosition = heap.add(evaluated);
        symTable.update(varName, new RefValue(newPosition, locationType));

        return state;
    }
}
