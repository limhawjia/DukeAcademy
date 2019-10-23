package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.exceptions.ParseException;

/**
 * Implementation of the CommandLogic interface. This implementation uses a helper class, CommandParser to help
 * parse its commands. Any commands to be executed by this CommandLogic implementation has to be registered through
 * the registerCommand method.
 */
public class CommandLogicManager implements CommandLogic {
    private CommandParser commandParser;

    public CommandLogicManager() {
        this.commandParser = new CommandParser();
    }

    /**
     * Registers a command that will now be executed by this CommandLogic instance.
     * @param commandWord the keyword of the command.
     * @param commandSupplier a supplier of command instances.
     */
    public void registerCommand(String commandWord, CommandSupplier commandSupplier) {
        this.commandParser.registerCommand(commandWord, commandSupplier);
    }

    @Override
    public CommandResult executeCommand(String commandText) throws ParseException, CommandException {
        Command command = this.commandParser.parseCommandText(commandText);
        return command.execute();
    }
}
