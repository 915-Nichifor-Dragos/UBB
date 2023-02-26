package Model.Statement;

import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.StatementException;

public class CompStmt implements IStmt {

    IStmt first;
    IStmt snd;

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    public CompStmt(IStmt first, IStmt second)
    {
        this.first = first;
        this.snd = second;
    }

    public PrgState execute(PrgState state) throws StatementException {

        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);

        return state;
    }


}
