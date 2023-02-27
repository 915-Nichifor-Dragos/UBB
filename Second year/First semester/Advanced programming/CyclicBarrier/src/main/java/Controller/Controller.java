package Controller;

import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller {

    IRepository repository;
    int display;
    ExecutorService executorService;

    public Controller(IRepository repo) {
        repository = repo;
    }

    public void display(PrgState state) throws ADTException {
        if (display == 1)
            System.out.println(state.programStateToString());
    }

    public void setDisplayFlag(int displayFlag) {
        this.display = displayFlag;
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

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymbolTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                        .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<PrgState> programStates) throws InterruptedException {
        programStates.forEach(programState -> {
            try {
                repository.logPrgStateExec(programState);
                display(programState);
            } catch (IOException | ADTException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        List<Callable<PrgState>> callList = programStates.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newProgramList = executorService.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                    return null;
                })
                .filter(Objects::nonNull).toList();

        programStates.addAll(newProgramList);

        programStates.forEach(programState -> {
            try {
                if (programState.getStack().isEmpty()) {
                    repository.logPrgStateExec(programState);
                    display(programState);
                }
            } catch (IOException | ADTException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        repository.setPrgList(programStates);
    }

    public void oneStep() throws InterruptedException {
        executorService = Executors.newFixedThreadPool(2);
        //List<PrgState> programStates = repository.getPrgList();
        List<PrgState> programStates = removeCompletedPrg(repository.getPrgList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        executorService.shutdownNow();
    }

    public void allStep() throws InterruptedException, ExpressionException, ADTException, StatementException, IOException {
        executorService = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repository.getPrgList());
        while (programStates.size() > 0) {
            oneStepForAllPrograms(programStates);
            conservativeGarbageCollector(programStates);
            programStates = removeCompletedPrg(repository.getPrgList());
        }
        executorService.shutdownNow();
        repository.setPrgList(programStates);
    }

    public void setProgramStates(List<PrgState> programStates) {
        this.repository.setPrgList(programStates);
    }
    public List<PrgState> getProgramStates() {
        return this.repository.getPrgList();
    }


}

