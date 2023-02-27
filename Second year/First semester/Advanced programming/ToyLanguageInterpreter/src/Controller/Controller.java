package Controller;

import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;
import Model.Statement.*;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    IRepository repository;
    int display;

    public Controller(IRepository repo) {
        repository = repo;
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> (symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public PrgState oneStep(PrgState state) throws StatementException, ADTException, ExpressionException {

        MyIStack<IStmt> stk = state.getStk();
        if (stk.isEmpty())
            throw new ADTException("Program state stack is empty");

        IStmt crtStmt = stk.pop();
        state.setExeStack(stk);
        return crtStmt.execute(state);

    }

    public void allStep() throws StatementException, ExpressionException, ADTException, IOException {

        PrgState program = this.repository.getCrtPrg();
        this.repository.logPrgStateExec();
        this.display();
        while (!program.getStk().isEmpty()) {
            program = oneStep(program);
            this.repository.logPrgStateExec();
            program.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(getAddrFromSymTable(program.getSymTable().getContent().values()), getAddrFromHeap(program.getHeap().getContent().values()), program.getHeap().getContent()));
            this.display();
        }
    }

    public void display() throws ADTException {
        if (display == 1)
            System.out.println(this.repository.getCrtPrg().programStateToString());
    }

    public void setDisplayFlag(int displayFlag) {
        this.display = displayFlag;
    }

}