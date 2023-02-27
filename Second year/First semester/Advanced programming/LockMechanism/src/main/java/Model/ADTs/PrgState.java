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
    MyIDictionary<String, Value> symbolTable;
    MyIList<Value> outTable;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIHeap heap;
    MyILockTable lockTable;
    int id;
    static int lastId;

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symTbl, MyIList<Value> outTbl,
                    MyIDictionary<String, BufferedReader> fileTbl, MyIHeap heapTbl, MyILockTable lockTable, IStmt prg)
    {
        exeStack = stack;
        symbolTable = symTbl;
        outTable = outTbl;
        fileTable = fileTbl;
        heap = heapTbl;
        this.lockTable = lockTable;
        this.setId();
        this.addStatement(prg);
    }

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symTbl, MyIList<Value> outTbl,
                    MyIDictionary<String, BufferedReader> fileTbl, MyIHeap heapTbl, MyILockTable lockTable) {
        exeStack = stack;
        symbolTable = symTbl;
        outTable = outTbl;
        fileTable = fileTbl;
        heap = heapTbl;
        this.lockTable = lockTable;
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

    public MyIDictionary<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public MyIList<Value> getOutTable() {
        return this.outTable;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyILockTable getLockTable() {return this.lockTable;}

    public MyIHeap getHeap() {
        return this.heap;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
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
        List<Value> outList = outTable.getList();
        for (Value element : outList) {
            string.append(element.toString());
            string.append("\n");
        }
        return string.toString();
    }

    public String symTableToString() throws ADTException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key : symbolTable.keySet()) {
            symTableStringBuilder.append(String.format("%s -> %s\n", key, symbolTable.lookUp(key).toString()));
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

    public String lockTableToString() throws ADTException {
        StringBuilder lockStringBuilder = new StringBuilder();
        for (int key : lockTable.keySet()) {
            lockStringBuilder.append(String.format("%d -> %s\n", key, lockTable.get(key)));
        }
        return lockStringBuilder.toString();
    }

    @Override
    public String toString() {
        return "\nProgram id: " + getId() + "\nExecution stack: " + this.exeStack.getReversed() + "\nSymbol table: " + this.symbolTable.toString()
                + "\nOutput list: " + this.outTable.toString() + "\nFile table: " + this.fileTable.toString() + "\nLock table: " + this.lockTable.toString() + "\n"
                + "\nHeap: " + this.heap.toString();
    }

    public String programStateToString() throws ADTException {
        return "Program id: " + getId() + "\n" + "Execution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n"
                + outToString() + "File table: \n" + fileTableToString() + "Heap: \n" + heapToString() + "Lock table: \n" + lockTableToString() + "\n";
    }

    public PrgState oneStep() throws StatementException, ADTException, ExpressionException {

        if (exeStack.isEmpty())
            throw new ADTException("Program state stack is empty");

        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);

    }

}