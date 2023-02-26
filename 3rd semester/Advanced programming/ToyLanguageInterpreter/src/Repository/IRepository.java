package Repository;

import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;

import java.io.IOException;
import java.util.List;

public interface IRepository {

    PrgState getCrtPrg();

    void add_state(PrgState newState);

    void logPrgStateExec() throws IOException, ADTException;

    void emptyLogFile() throws IOException;

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> newPrograms);

}
