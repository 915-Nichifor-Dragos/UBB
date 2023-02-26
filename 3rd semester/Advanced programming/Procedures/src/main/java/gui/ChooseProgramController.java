package gui;

import Controller.Controller;
import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.*;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Value.IntValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChooseProgramController {

    private MyIProcTable procTable;
    private ExecutorProgramController programExecutorController;

    @FXML
    private ListView<IStmt> programListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize()
    {
        this.procTable = new MyProcTable();
        programListView.setItems(getAllPrograms());
        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void setProgramExecutorController(ExecutorProgramController executorController)
    {
        this.programExecutorController = executorController;
    }

    public void displayOnButtonClick(ActionEvent actionEvent)
    {
        IStmt selectedStatement = programListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement was selected!");
            alert.showAndWait();
        }
        else {
            int id = programListView.getSelectionModel().getSelectedIndex();
            try {
                MyIStack<MyIDictionary<String, Value>> symTableStack = new MyStack<>();
                symTableStack.push(new MyDictionary<>());
                PrgState programState = new PrgState(new MyStack<>(), symTableStack, new MyList<>(), new MyDictionary<>(), new MyHeap(), this.procTable, selectedStatement);
                IRepository repository = new Repository(programState, "src/main/java/Files/log" + id + ".txt");
                Controller controller = new Controller(repository);
                programExecutorController.setController(controller);

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<IStmt> getAllPrograms () {
        List<IStmt> programs = new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        try {
            ex1.typeCheck(new MyDictionary<>());
            programs.add(ex1);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt sumProc = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp(1, new VarExp("a"), new VarExp("b"))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        List<String> varSum = new ArrayList<>();
        varSum.add("a");
        varSum.add("b");
        procTable.put("sum", new javafx.util.Pair<>(varSum, sumProc));

        IStmt prodProc = new CompStmt(
                new VarDeclStmt("v" , new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp(3, new VarExp("a"), new VarExp("b"))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        List<String> varProd = new ArrayList<>();
        varProd.add("a");
        varProd.add("b");
        procTable.put("product", new javafx.util.Pair<>(varProd, prodProc));

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("w", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new CompStmt(
                                        new AssignStmt("w", new ValueExp(new IntValue(5))),
                                        new CompStmt(
                                                new CallProcStmt("sum", new ArrayList<>(Arrays.asList(new ArithExp(3, new VarExp("v"), new ValueExp(new IntValue(10))), new VarExp("w")))),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new ForkStmt(
                                                                new CompStmt(
                                                                        new CallProcStmt("product", new ArrayList<>(Arrays.asList(new VarExp("v"), new VarExp("w")))),
                                                                        new ForkStmt(
                                                                                new CallProcStmt("sum", new ArrayList<>(Arrays.asList(new VarExp("v"), new VarExp("w"))))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            ex2.typeCheck(new MyDictionary<>());
            programs.add(ex2);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
        try {
            ex10.typeCheck(new MyDictionary<>());
            programs.add(ex10);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        return FXCollections.observableArrayList(programs);
    }

}
