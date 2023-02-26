package Model.Statement;

import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.Value;

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
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stack = state.getExeStack();
        if (!value.getType().equals(new BoolType()))
            throw new StatementException(String.format("%s is not of BoolType", value));
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getVal()) {
            stack.push(this);
            stack.push(statement);
        }
        return state;
    }

}
