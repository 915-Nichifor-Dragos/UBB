package Model.Statement;

import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

import java.util.Map;

public class WhileStmt implements IStmt {

    private final IExp expression;
    private final IStmt statement;

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }

    public WhileStmt(IExp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        Value value = expression.eval(state.getSymbolTable().peek(), state.getHeap());
        MyIStack<IStmt> stack = state.getStack();
        if (!value.getType().equals(new BoolType()))
            throw new StatementException(String.format("%s is not of BoolType", value));
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getVal()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            MyIDictionary <String, Type> typeEnv2 = new MyDictionary<>();
            for (Map.Entry<String, Type> entry: typeEnv.getContent().entrySet()) {
                typeEnv2.put(entry.getKey(), entry.getValue());
            }
            statement.typeCheck(typeEnv2);
            return typeEnv;
        } else
            throw new StatementException("The condition of WHILE does not have the type Bool.");
    }


    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), statement.deepCopy());
    }

}
