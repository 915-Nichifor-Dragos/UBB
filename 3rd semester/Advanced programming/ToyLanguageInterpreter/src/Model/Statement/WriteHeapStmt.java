package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Value.RefValue;
import Model.Value.Value;

public class WriteHeapStmt implements IStmt {

    private final String varName;
    private final IExp expression;

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }

    public WriteHeapStmt(String varName, IExp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new StatementException(String.format("%s not present in the symTable", varName));
        Value value = symTable.lookUp(varName);
        if (!(value instanceof RefValue))
            throw new StatementException(String.format("%s not of RefType", value));
        RefValue refValue = (RefValue) value;
        Value evaluated = expression.eval(symTable, heap);
        if (!evaluated.getType().equals(refValue.getLocationType()))
            throw new StatementException(String.format("%s not of %s", evaluated, refValue.getLocationType()));
        heap.update(refValue.getAddr(), evaluated);
        return state;
    }

}
