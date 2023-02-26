package Repository;

import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository  implements IRepository {

    PrgState currentPrg;
    List<PrgState> programs;
    private final String logFilePath;

    public Repository(PrgState program, String filePath) throws IOException {
        this.currentPrg = program;
        this.logFilePath = filePath;
        this.programs = new ArrayList<PrgState>();

        this.emptyLogFile();
        this.programs.add(program);
    }

    @Override
    public PrgState getCrtPrg() {
        return currentPrg;
    }

    @Override
    public void add_state(PrgState newState)
    {
        programs.add(newState);
    }

    @Override
    public void logPrgStateExec() throws IOException, ADTException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(currentPrg.programStateToString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }

    public List<PrgState> getPrgList()
    {
        return this.programs;
    }

    public void setPrgList(List<PrgState> newPrograms)
    {
        this.programs = newPrograms;
    }

}
