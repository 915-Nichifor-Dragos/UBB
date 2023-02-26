package View;

import Controller.Controller;
import Model.ADTs.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.StatementException;
import Model.Exceptions.ExpressionException;
import Model.Expression.ArithExp;
import Model.Expression.RelationalExp;
import Model.Expression.ValueExp;
import Model.Expression.VarExp;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import Model.ADTs.PrgState;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class View {

    public void menu() {
        System.out.println("\n1) Execute a hard-coded program.");
        System.out.println("0) Exit\n");
        System.out.println("Your command: ");
    }

    public void print_display_flag() {
        System.out.println("\n============== Display the states of the program? ==============\n");
        System.out.println("1) Yes");
        System.out.println("2) No");
        System.out.println("\nYour command: ");
    }

    public void print_already_coded_menu() {
        System.out.println("\n============== The programs are ==============\n");
        System.out.println("1) int v;\n   v = 2;\n   Print(v)\n");
        System.out.println("2) int a;\n   int b;\n   a = 2 + 3 * 5;\n   b = a + 1;\n   Print(b)\n");
        System.out.println("3) bool a;\n   int v;\n   a = true;\n   If (a Then v=2 Else v=3);\n   Print(v);\n");
        System.out.println("4) string varf;\n   varf = 'test.in';\n   openRFile(varf);\n   int varc;\n   " +
                "readFile(varf, varc);\n   print(varc);\n   readFile(varf, varc);\n   print(varc);\n   closeRFile(varf);\n");
        System.out.println("5) int a;\n   int b;\n   a = 5;\n   b = 7;\n   IF (a > b Then print(a) Else print(b));\n");
        System.out.println("0) Exit\n");
        System.out.println("Your command: ");
    }

    private void runProgram1(int display) throws StatementException, ExpressionException, ADTException, IOException {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        runStatement(ex1, display);
    }

    private void runProgram2(int display) throws StatementException, ExpressionException, ADTException, IOException {
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)), new
                                ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        runStatement(ex2, display);
    }

    private void runProgram3(int display) throws StatementException, ExpressionException, ADTException, IOException {
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        runStatement(ex3, display);
    }

    private void runProgram4(int display) throws StatementException, ExpressionException, ADTException, IOException {
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("src/Files/test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));
        runStatement(ex4, display);
    }

    private void runProgram5(int display) throws StatementException, ExpressionException, ADTException, IOException {
        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                                        new IfStmt(new RelationalExp(">", new VarExp("a"),
                                                new VarExp("b")),new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new VarExp("b")))))));
        runStatement(ex5, display);
    }

    private void runStatement(IStmt statement, int display) throws StatementException, ExpressionException, ADTException, IOException {
        MyIStack<IStmt> executionStack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyIList<Value> output = new MyList<>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();

        PrgState state = new PrgState(executionStack, symbolTable, output, fileTable, heap, statement);

        IRepository repository = new Repository(state, "src/Files/repositoryState.txt");
        Controller controller = new Controller(repository);
        controller.setDisplayFlag(display);

        controller.allStep();

    }

    public void executeInput() {

        while (true) {
            try {
                print_already_coded_menu();
                Scanner scanner = new Scanner(System.in);
                int command = scanner.nextInt();

                if (command == 0) {
                    System.out.println("\nExiting...");
                    return;
                }
                print_display_flag();
                int display = scanner.nextInt();

                if (command == 1)
                    runProgram1(display);
                if (command == 2)
                    runProgram2(display);
                if (command == 3)
                    runProgram3(display);
                if (command == 4)
                    runProgram4(display);
                if (command == 5)
                    runProgram5(display);
            } catch (StatementException | ExpressionException | ADTException | IOException e) {
                System.out.println(e.toString());
            }
        }

    }

    public void execute() {
        menu();
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();

        if (command == 0) {
            System.out.println("\nExiting...");
        }
        if (command == 1)
            executeInput();
    }
}
