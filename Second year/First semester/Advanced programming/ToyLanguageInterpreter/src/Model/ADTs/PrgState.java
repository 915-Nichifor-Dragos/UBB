package Model.ADTs;

import Model.Exceptions.ADTException;
import Model.Statement.IStmt;
import Model.Value.Value;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {


    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIHeap heap;

    IStmt originalProgram;


    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }


    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symTbl, MyIList<Value> ot,
                    MyIDictionary<String, BufferedReader> fT, MyIHeap hp, IStmt prg) {
        exeStack = stk;
        symTable = symTbl;
        out = ot;
        fileTable = fT;
        heap = hp;

        this.exeStack.push(prg);
    }


    public MyIStack<IStmt> getStk() {
        return this.exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return this.symTable;
    }

    public MyIList<Value> getOut() {
        return this.out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyIHeap getHeap() {
        return this.heap;
    }

    public void addStatement(IStmt statement) {
        this.exeStack.push(statement);
    }

    public String exeStackToString() {
        StringBuilder exeStackStringBuilder = new StringBuilder();
        List<IStmt> stack = exeStack.getReversed();
        for (IStmt statement : stack) {
            exeStackStringBuilder.append(statement.toString()).append("\n");
        }
        return exeStackStringBuilder.toString();
    }

    public String outToString() {
        StringBuilder string = new StringBuilder();
        List<Value> outList = out.getList();
        for (Value element : outList) {
            string.append(element.toString());
            string.append("\n");
        }
        return string.toString();
    }

    public String symTableToString() throws ADTException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key : symTable.keySet()) {
            symTableStringBuilder.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String fileTableToString() throws ADTException {
        StringBuilder string = new StringBuilder();
        for (String key : fileTable.keySet()) {
            string.append(String.format("%s -> %s\n", key, fileTable.lookUp(key).toString()));
        }
        return string.toString();
    }

    public String heapToString() throws ADTException {
        StringBuilder heapStringBuilder = new StringBuilder();
        for (int key: heap.keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heap.get(key)));
        }
        return heapStringBuilder.toString();
    }

    @Override
    public String toString() {
        return "\nExecution stack: " + this.exeStack.getReversed() + "\nSymbol table: " + this.symTable.toString()
                + "\nOutput list: " + this.out.toString() + "\nFile table: " + this.fileTable.toString() + "\n"
                + "\nHeap: " + this.heap.toString();
    }

    public String programStateToString() throws ADTException {
        return "Execution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n"
                + outToString() + "File table: \n" + fileTableToString() + "Heap: \n" + heapToString();
    }
}
