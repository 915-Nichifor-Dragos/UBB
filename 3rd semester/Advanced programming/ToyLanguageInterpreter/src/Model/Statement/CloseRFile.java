package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {

    IExp exp;

    public CloseRFile(IExp exp1) {
        exp = exp1;
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", exp.toString());
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap heap = state.getHeap();
        Value value = exp.eval(symTbl, heap);

        if (!value.getType().equals(new StringType()))
            throw new StatementException(String.format("%s does not evaluate to StringValue", exp));
        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName.getVal()))
            throw new StatementException(String.format("%s is not present in the FileTable", value));
        BufferedReader br = fileTable.lookUp(fileName.getVal());
        try {
            br.close();
        } catch (IOException e) {
            throw new StatementException(String.format("Unexpected error in closing %s", value));
        }
        fileTable.remove(fileName.getVal());

        return state;
    }
}
