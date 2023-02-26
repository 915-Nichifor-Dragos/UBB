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
            PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex1);
            IRepository repo1 = new Repository(prg1, "src/main/java/Files/log1.txt");
            Controller controller1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), controller1));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
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
            PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex2);
            IRepository repo2 = new Repository(prg2, "src/main/java/Files/log2.txt");
            Controller controller2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), controller2));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
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
            PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex3);
            IRepository repo3 = new Repository(prg3, "src/main/java/Files/log3.txt");
            Controller controller3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(), controller3));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("src/Files/test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));
        try {
            ex4.typeCheck(new MyDictionary<>());
            PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex4);
            IRepository repo4 = new Repository(prg4, "src/main/java/Files/log4.txt");
            Controller controller4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), controller4));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
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
            PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex5);
            IRepository repo5 = new Repository(prg5, "src/main/java/Files/log5.txt");
            Controller controller5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(), controller5));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))), new PrintStmt(new ArithExp(1, new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        try {
            ex6.typeCheck(new MyDictionary<>());
            PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex6);
            IRepository repo6 = new Repository(prg6, "src/main/java/Files/log6.txt");
            Controller controller6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", ex6.toString(), controller6));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(1, new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        try {
            ex7.typeCheck(new MyDictionary<>());
            PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex7);
            IRepository repo7 = new Repository(prg7, "src/main/java/Files/log7.txt");
            Controller controller7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", ex7.toString(), controller7));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        try {
            ex8.typeCheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex8);
            IRepository repo8 = new Repository(prg8, "src/main/java/Files/log8.txt");
            Controller controller8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", ex8.toString(), controller8));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
        try {
            ex9.typeCheck(new MyDictionary<>());
            PrgState prg9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex9);
            IRepository repo9 = new Repository(prg9, "src/main/java/Files/log9.txt");
            Controller controller9 = new Controller(repo9);
            menu.addCommand(new RunExample("9", ex9.toString(), controller9));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
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
            PrgState prg10 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex10);
            IRepository repo10 = new Repository(prg10, "src/main/java/Files/log10.txt");
            Controller controller10 = new Controller(repo10);
            menu.addCommand(new RunExample("10", ex10.toString(), controller10));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
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
            PrgState prg11 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex11);
            IRepository repo11 = new Repository(prg11, "src/main/java/Files/log11.txt");
            Controller controller11 = new Controller(repo11);
            menu.addCommand(new RunExample("11", ex11.toString(), controller11));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
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
            PrgState prg12 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex12);
            IRepository repo12 = new Repository(prg12, "src/main/java/Files/log12.txt");
            Controller controller12 = new Controller(repo12);
            menu.addCommand(new RunExample("12", ex12.toString(), controller12));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // FOR STATEMENT
        IStmt ex13 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(new NewStmt("a", new ValueExp(new IntValue(20))), new CompStmt(
                new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)), new ArithExp(1, new VarExp("v"), new ValueExp(new IntValue(1))),
                        new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(3, new VarExp("v"), new ReadHeapExp(new VarExp("a"))))))),
                new PrintStmt(new ReadHeapExp(new VarExp("a"))))));
        try {
            ex13.typeCheck(new MyDictionary<>());
            PrgState prg13 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex13);
            IRepository repo13 = new Repository(prg13, "src/main/java/Files/log13.txt");
            Controller controller13 = new Controller(repo13);
            menu.addCommand(new RunExample("13", ex13.toString(), controller13));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
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
            PrgState prg14 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex14);
            IRepository repo14 = new Repository(prg14, "src/main/java/Files/log14.txt");
            Controller controller14 = new Controller(repo14);
            menu.addCommand(new RunExample("14", ex14.toString(), controller14));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // SLEEP STATEMENT
        IStmt ex15 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))),
                        new CompStmt(new AssignStmt("v", new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("v"))))),
                        new CompStmt(new SleepStmt(10), new PrintStmt(new ArithExp(3, new VarExp("v"), new ValueExp(new IntValue(10))))))));
        try {
            ex15.typeCheck(new MyDictionary<>());
            PrgState prg15 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex15);
            IRepository repo15 = new Repository(prg15, "src/main/java/Files/log15.txt");
            Controller controller15 = new Controller(repo15);
            menu.addCommand(new RunExample("15", ex15.toString(), controller15));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // WAIT STATEMENT
        IStmt ex16 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new WaitStmt(10) , new PrintStmt(new ArithExp(3, new VarExp("v"), new ValueExp(new IntValue(10)))))));
        try {
            ex16.typeCheck(new MyDictionary<>());
            PrgState prg16 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex16);
            IRepository repo16 = new Repository(prg16, "src/main/java/Files/log16.txt");
            Controller controller16 = new Controller(repo16);
            menu.addCommand(new RunExample("16", ex16.toString(), controller16));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // MUL EXPRESSION
        IStmt ex17 = new CompStmt(new VarDeclStmt("v1", new IntType()), new CompStmt(new AssignStmt("v1", new ValueExp(new IntValue(2))),
                new CompStmt(new VarDeclStmt("v2", new IntType()), new CompStmt(new AssignStmt("v2", new ValueExp(new IntValue(3))),
                        new PrintStmt(new MulExp(new VarExp("v1"), new VarExp("v2")))))));
        try {
            ex17.typeCheck(new MyDictionary<>());
            PrgState prg17 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex17);
            IRepository repo17 = new Repository(prg17, "src/main/java/Files/log17.txt");
            Controller controller17 = new Controller(repo17);
            menu.addCommand(new RunExample("17", ex17.toString(), controller17));
        } catch (IOException | ExpressionException | ADTException | StatementException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        menu.show();
    }

}