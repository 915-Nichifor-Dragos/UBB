package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.IExp;
import Model.Expression.RelationalExp;
import Model.Expression.VarExp;
import Model.Type.IntType;
import Model.Type.Type;

import java.util.Objects;

public class ForStmt implements IStmt {

    String id;
    IExp exp1, exp2, exp3;
    IStmt stmt;

    public ForStmt(String id, IExp exp1, IExp exp2, IExp exp3, IStmt stmt)
    {
        this.id = id;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public String toString() {return "for(" + id + "=" + exp1 + ";" + id + "<" + exp2 + ";" + id + "=" + exp3 + ") " + stmt; }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException {
        MyIStack<IStmt> stk = state.getStack();
        stk.push(new CompStmt(new VarDeclStmt(id, new IntType()), new CompStmt(new AssignStmt(id, exp1),
                new WhileStmt(new RelationalExp("<", new VarExp(id), exp2), new CompStmt(stmt, new AssignStmt(id, exp3))))));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ExpressionException, ADTException {
        typeEnv.put(id, new IntType());
        if (! Objects.equals(exp1.typeCheck(typeEnv).toString(), "int") || ! Objects.equals(exp2.typeCheck(typeEnv).toString(), "int") ||
                ! Objects.equals(exp3.typeCheck(typeEnv).toString(), "int"))
            throw new ExpressionException("Not of type int");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(id, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }
}
