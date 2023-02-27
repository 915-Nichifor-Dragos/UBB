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
import Repository.IRepository;
import Repository.Repository;
import View.Commands.ExitCommand;

import java.io.IOException;

class Interpreter {

    public static void main(String[] args) throws ADTException, IOException {

        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        try {
            ex1.typeCheck(new MyDictionary<>());
            PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), new MyLockTable(), ex1);
            IRepository repo1 = new Repository(prg1, "src/main/java/Files/log1.txt");
            Controller controller1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), controller1));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex11 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
               new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(new VarDeclStmt("q", new IntType()), new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                       new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))), new CompStmt(new NewLockStmt("x"), new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("x"),
                               new CompStmt(new WriteHeapStmt("v1", new ArithExp(2, new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))), new UnlockStmt("x")))),
                               new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1", new ArithExp(3, new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))), new UnlockStmt("x"))))),
                               new CompStmt(new NewLockStmt("q"), new CompStmt(new ForkStmt (new CompStmt(new ForkStmt(new CompStmt(new LockStmt("q"), new CompStmt(new WriteHeapStmt("v2", new ArithExp(1, new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)))),
                                       new UnlockStmt("q")))), new CompStmt(new LockStmt("q"), new CompStmt(new WriteHeapStmt("v2", new ArithExp(3, new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                       new UnlockStmt("q"))))), new CompStmt(new NopeStmt("1"), new CompStmt(new NopeStmt("2"), new CompStmt(new NopeStmt("3"), new CompStmt(new NopeStmt("4"), new CompStmt(new LockStmt("x"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                       new CompStmt(new UnlockStmt("x"), new CompStmt(new LockStmt("q"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))), new UnlockStmt("q"))))))))))))))))))));
        try {
            ex11.typeCheck(new MyDictionary<>());
            PrgState prg11 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), new MyLockTable(), ex11);
            IRepository repo11 = new Repository(prg11, "src/main/java/Files/log11.txt");
            Controller controller11 = new Controller(repo11);
            menu.addCommand(new RunExample("11", ex11.toString(), controller11));
        } catch (IOException | ADTException | StatementException | ExpressionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        menu.show();
    }

}