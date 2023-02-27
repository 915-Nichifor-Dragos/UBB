package View.Commands;

import Model.Exceptions.ADTException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;

import java.io.IOException;

public abstract class Command {
    private final String key, description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public abstract void execute() throws StatementException, ADTException, IOException, ExpressionException;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}

