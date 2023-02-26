package gui;

import Controller.Controller;
import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.Expression.*;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.RunExample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ChooseProgramController {

    private ExecutorProgramController programExecutorController;

    @FXML
    private ListView<IStmt> programListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize()
    {
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
                PrgState programState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), selectedStatement);
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


        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)), new
                                ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        try {
            ex2.typeCheck(new MyDictionary<>());
            programs.add(ex2);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        try {
            ex3.typeCheck(new MyDictionary<>());
            programs.add(ex3);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("src/main/java/Files/test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));
        try {
            ex4.typeCheck(new MyDictionary<>());
            programs.add(ex4);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                                        new IfStmt(new RelationalExp(">", new VarExp("a"),
                                                new VarExp("b")), new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new VarExp("b")))))));
        try {
            ex5.typeCheck(new MyDictionary<>());
            programs.add(ex5);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))), new PrintStmt(new ArithExp(1, new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        try {
            ex6.typeCheck(new MyDictionary<>());
            programs.add(ex6);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(1, new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        try {
            ex7.typeCheck(new MyDictionary<>());
            programs.add(ex7);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        try {
            ex8.typeCheck(new MyDictionary<>());
            programs.add(ex8);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
        try {
            ex9.typeCheck(new MyDictionary<>());
            programs.add(ex9);
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

        // SWITCH STATEMENT
        IStmt ex11 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                        new CompStmt(new VarDeclStmt("b", new IntType()),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))),
                                        new CompStmt(new VarDeclStmt("c", new IntType()),
                                                new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                        new CompStmt(new SwitchStmt(new ArithExp(3, new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                new ArithExp(3, new VarExp("b"), new VarExp("c")), new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                                                new ValueExp(new IntValue(10)), new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))), new PrintStmt(new ValueExp(new IntValue(300))) )))))));
        try {
            ex11.typeCheck(new MyDictionary<>());
            programs.add(ex11);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // CONDITIONAL ASSIGN STATEMENT
        IStmt ex12 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("b", new RefType(new IntType())), new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new NewStmt("a", new ValueExp(new IntValue(0))), new CompStmt(new NewStmt("b", new ValueExp(new IntValue(0))),
                                new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(1))), new CompStmt(new WriteHeapStmt("b", new ValueExp(new IntValue(2))),
                                        new CompStmt(new CondAssignStmt("v", new RelationalExp("<", new ReadHeapExp(new VarExp("a")), new ReadHeapExp(new VarExp("b"))),
                                                new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))), new CompStmt(new PrintStmt(new VarExp("v")),
                                                new CompStmt(new CondAssignStmt("v", new RelationalExp(">", new ArithExp(2, new ReadHeapExp(new VarExp("b")), new ValueExp(new IntValue(2))), new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))), new PrintStmt(new VarExp("v"))))))))))));
        try {
            ex12.typeCheck(new MyDictionary<>());
            programs.add(ex12);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // FOR STATEMENT
        IStmt ex13 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(new NewStmt("a", new ValueExp(new IntValue(20))), new CompStmt(
                new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)), new ArithExp(1, new VarExp("v"), new ValueExp(new IntValue(1))),
                        new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(3, new VarExp("v"), new ReadHeapExp(new VarExp("a"))))))),
                new PrintStmt(new ReadHeapExp(new VarExp("a"))))));
        try {
            ex13.typeCheck(new MyDictionary<>());
            programs.add(ex13);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // REPEAT UNTIL STATEMENT
        IStmt ex14 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(new VarDeclStmt("y", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))), new CompStmt(new RepeatUntilStmt(new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))), new AssignStmt("v", new ArithExp(1, new VarExp("v"), new ValueExp(new IntValue(1))))),
                        new RelationalExp("==", new VarExp("v"), new ValueExp(new IntValue(3)))), new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),
                        new CompStmt(new NopeStmt("asd"), new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(3))), new CompStmt(new NopeStmt("ads"), new PrintStmt(new ArithExp(3, new VarExp("v"), new ValueExp(new IntValue(10)))))))))))));
        try {
            ex14.typeCheck(new MyDictionary<>());
            programs.add(ex14);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // SLEEP STATEMENT
        IStmt ex15 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))),
                        new CompStmt(new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("v"))))),
                        new CompStmt(new SleepStmt(10), new PrintStmt(new ArithExp(3, new VarExp("v"), new ValueExp(new IntValue(10))))))));
        try {
            ex15.typeCheck(new MyDictionary<>());
            programs.add(ex15);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // WAIT STATEMENT
        IStmt ex16 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new WaitStmt(10) , new PrintStmt(new ArithExp(3, new VarExp("v"), new ValueExp(new IntValue(10)))))));
        try {
            ex16.typeCheck(new MyDictionary<>());
            programs.add(ex16);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // MUL EXPRESSION
        IStmt ex17 = new CompStmt(new VarDeclStmt("v1", new IntType()), new CompStmt(new AssignStmt("v1", new ValueExp(new IntValue(2))),
                new CompStmt(new VarDeclStmt("v2", new IntType()), new CompStmt(new AssignStmt("v2", new ValueExp(new IntValue(3))),
                        new PrintStmt(new MulExp(new VarExp("v1"), new VarExp("v2")))))));
        try {
            ex17.typeCheck(new MyDictionary<>());
            programs.add(ex17);
        } catch (ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        return FXCollections.observableArrayList(programs);
    }

}
