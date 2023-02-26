package View;

import Controller.Controller;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import View.Commands.Command;


import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunExample extends Command {
    private final Controller ctr;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute(){
        System.out.println("Do you want to display the steps?[Y/n]");
        Scanner readOption = new Scanner(System.in);
        String option = readOption.next();
        if (Objects.equals(option, "Y"))
            ctr.setDisplayFlag(1);
        try {
            ctr.allStep();
        } catch (InterruptedException | ExpressionException | ADTException | StatementException | IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

}