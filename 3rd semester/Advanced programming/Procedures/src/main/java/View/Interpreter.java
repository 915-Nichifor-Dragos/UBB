package View;

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
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import View.Commands.ExitCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Interpreter {

    public static void main(String[] args) throws ADTException, IOException {

        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand("0", "exit"));

        MyIProcTable procTable = new MyProcTable();

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


        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        try {
            ex1.typeCheck(new MyDictionary<>());
            MyIStack<MyIDictionary<String, Value>> symTableStack = new MyStack<>();
            symTableStack.push(new MyDictionary<>());
            PrgState prg1 = new PrgState(new MyStack<>(), symTableStack, new MyList<>(), new MyDictionary<>(), new MyHeap(), procTable, ex1);
            IRepository repo1 = new Repository(prg1, "src/main/java/Files/log1.txt");
            Controller controller1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), controller1));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

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
            MyIStack<MyIDictionary<String, Value>> symTableStack = new MyStack<>();
            symTableStack.push(new MyDictionary<>());
            PrgState prg2 = new PrgState(new MyStack<>(), symTableStack, new MyList<>(), new MyDictionary<>(), new MyHeap(), procTable, ex2);
            IRepository repo2 = new Repository(prg2, "src/main/java/Files/log2.txt");
            Controller controller2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), controller2));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        menu.show();
    }

}