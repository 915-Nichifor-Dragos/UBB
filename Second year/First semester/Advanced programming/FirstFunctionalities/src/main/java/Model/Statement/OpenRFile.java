package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {

    IExp expression;

    public OpenRFile(IExp exp1) {
        expression = exp1;
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", expression.toString());
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {

        MyIDictionary<String, Value> symTbl = state.getSymbolTable();
        MyIHeap heap = state.getHeap();
        Value value = expression.eval(symTbl, heap);

        if (value.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fileName.getVal())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getVal()));
                } catch (FileNotFoundException e) {
                    throw new StatementException(String.format("%s could not be opened", fileName.getVal()));
                }
                fileTable.put(fileName.getVal(), br);
            } else {
                throw new StatementException(String.format("%s is already opened", fileName.getVal()));
            }
        } else {
            throw new StatementException(String.format("%s does not evaluate to StringType", expression.toString()));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new StatementException("OpenReadFile requires a string expression.");
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(expression.deepCopy());
    }

}
