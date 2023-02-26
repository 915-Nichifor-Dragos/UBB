package Model.ADTs;

import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Statement.IStmt;
import Model.Value.Value;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {

    MyIStack<IStmt> exeStack;
    MyIStack<MyIDictionary<String, Value>> symbolTable;
    MyIList<Value> outTable;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIHeap heap;
    MyIProcTable procTable;
    int id;
    static int lastId;

    public PrgState(MyIStack<IStmt> stack, MyIStack<MyIDictionary<String, Value>> symTable, MyIList<Value> outTbl,
                    MyIDictionary<String, BufferedReader> fileTbl, MyIHeap heapTbl, MyIProcTable procTable, IStmt prg) {
        exeStack = stack;
        symbolTable = symTable;
        outTable = outTbl;
        fileTable = fileTbl;
        heap = heapTbl;
        this.procTable = procTable;
        this.setId();
        this.addStatement(prg);
    }

    public PrgState(MyIStack<IStmt> stack, MyIStack<MyIDictionary<String, Value>> symTable, MyIList<Value> outTbl,
                    MyIDictionary<String, BufferedReader> fileTbl, MyIHeap heapTbl, MyIProcTable procTable) {
        exeStack = stack;
        symbolTable = symTable;
        outTable = outTbl;
        fileTable = fileTbl;
        this.procTable = procTable;
        heap = heapTbl;
        this.setId();
    }

    public synchronized void setId() {
        this.id = lastId;
        lastId ++;
    }

    public int getId() {
        return id;
    }

    public MyIStack<IStmt> getStack() {
        return this.exeStack;
    }

    public MyIStack<MyIDictionary<String, Value>> getSymbolTable() {
        return this.symbolTable;
    }

    public MyIList<Value> getOutTable() {
        return this.outTable;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyIHeap getHeap() {
        return this.heap;
    }

    public MyIProcTable getProcTable() {return this.procTable;}

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public void addStatement(IStmt statement) {
        this.exeStack.push(statement);
    }

    public MyIDictionary<String, Value> getTopSymTable() {
        try {
            return symbolTable.peek();
        } catch (ADTException e) {
            System.out.println("Stack is empty!");
            return null;
        }
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
        List<Value> outList = outTable.getList();
        for (Value element : outList) {
            string.append(element.toString());
            string.append("\n");
        }
        return string.toString();
    }

    public String symTableToString() throws ADTException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        List<MyIDictionary<String, Value>> stack = symbolTable.getReversed();
        for (MyIDictionary<String, Value> table: stack) {
            for (String key: table.keySet()) {
                symTableStringBuilder.append(String.format("%s -> %s\n", key, table.lookUp(key).toString()));
            }
            symTableStringBuilder.append("\n");
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
        for (int key : heap.keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heap.get(key)));
        }
        return heapStringBuilder.toString();
    }

    public String procTableToString() throws ADTException {
        StringBuilder procTableStringBuilder = new StringBuilder();
        for (String key: procTable.keySet()) {
            procTableStringBuilder.append(String.format("%s -> Params: %s, Statement: %s\n", key, procTable.lookUp(key).getKey(), procTable.lookUp(key).getValue()));
        }
        return procTableStringBuilder.toString();
    }

    @Override
    public String toString() {
        return "\nProgram id: " + getId() + "\nExecution stack: " + this.exeStack.getReversed() + "\nSymbol table: " + this.symbolTable.toString()
                + "\nOutput list: " + this.outTable.toString() + "\nFile table: " + this.fileTable.toString() + "\n"
                + "\nHeap: " + this.heap.toString() + "\nProcedure table: " + this.procTable.toString();
    }

    public String programStateToString() throws ADTException {
        return "Program id: " + getId() + "\n" + "Execution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n"
                + outToString() + "File table: \n" + fileTableToString() + "Heap: \n" + heapToString() + "Procedure table: \n" + procTableToString();
    }

    public PrgState oneStep() throws StatementException, ADTException, ExpressionException {

        if (exeStack.isEmpty())
            throw new ADTException("Program state stack is empty");

        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);

    }

}
