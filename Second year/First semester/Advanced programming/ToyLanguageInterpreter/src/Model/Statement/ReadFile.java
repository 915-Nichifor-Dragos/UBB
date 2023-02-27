package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;


public class ReadFile implements IStmt {

    private final IExp exp;
    private final String varName;

    public ReadFile(IExp expression, String varName) {
        this.exp = expression;
        this.varName = varName;
    }

    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", exp.toString(), varName);
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap heap = state.getHeap();

        if (symTable.isDefined(varName)) {
            Value value = symTable.lookUp(varName);
            if (value.getType().equals(new IntType())) {
                value = exp.eval(symTable, heap);
                if (value.getType().equals(new StringType())) {
                    StringValue castValue = (StringValue) value;
                    if (fileTable.isDefined(castValue.getVal())) {
                        BufferedReader br = fileTable.lookUp(castValue.getVal());
                        try {
                            String line = br.readLine();
                            if (line == null)
                                line = "0";
                            symTable.update(varName, new IntValue(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new StatementException(String.format("Could not read from file %s", castValue));
                        }
                    } else {
                        throw new StatementException(String.format("The file table does not contain %s", castValue));
                    }
                } else {
                    throw new StatementException(String.format("%s does not evaluate to StringType", value));
                }
            } else {
                throw new StatementException(String.format("%s is not of type IntType", value));
            }
        } else {
            throw new StatementException(String.format("%s is not present in the symTable.", varName));
        }
        return state;
    }

}
