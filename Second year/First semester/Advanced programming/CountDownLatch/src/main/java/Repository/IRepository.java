package Repository;

import Model.ADTs.PrgState;
import Model.Exceptions.ADTException;

import java.io.IOException;
import java.util.List;

public interface IRepository {

    void add_state(PrgState newState);
    void logPrgStateExec(PrgState state) throws IOException, ADTException;
    void emptyLogFile() throws IOException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newPrograms);

}
