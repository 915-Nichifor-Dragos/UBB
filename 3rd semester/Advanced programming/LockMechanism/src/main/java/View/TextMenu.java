package View;

import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import View.Commands.Command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    private final MyIDictionary<String, Command> commands;

    public TextMenu() {
        commands = new MyDictionary<>();
    }

    public void addCommand(Command c) throws ADTException {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command com : commands.values()) {
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            try {
                Command command = commands.lookUp(key);
                command.execute();
            } catch (StatementException | ADTException | IOException | ExpressionException e){
                System.out.println("Invalid option. Exception: " + e);
            }
        }
    }

}
