package com.epam.library.controller;

import java.util.HashMap;
import java.util.Map;

import com.epam.library.command.Command;
import com.epam.library.command.CommandName;
import com.epam.library.command.impl.AddBook;
import com.epam.library.command.impl.DeleteBook;
import com.epam.library.command.impl.FillEmployeeBook;
import com.epam.library.command.impl.GetBook;
import com.epam.library.command.impl.GetEmployeesWithLQThenTwoBooks;
import com.epam.library.command.impl.GetEmployeesWithMoreThenOneBook;
import com.epam.library.command.impl.RenameBook;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();

	public CommandProvider() {
		commands.put(CommandName.ADD_BOOK, new AddBook());
		commands.put(CommandName.GET_BOOK, new GetBook());
		commands.put(CommandName.DELETE_BOOK, new DeleteBook());
		commands.put(CommandName.RENAME_BOOK, new RenameBook());
		commands.put(CommandName.GET_EMPLOYEES_WITH_MORE_THAN_ONE_BOOK, new GetEmployeesWithMoreThenOneBook());
		commands.put(CommandName.GET_EMPLOYEES_WITH_LESS_EQUALS_TWO_BOOKS, new GetEmployeesWithLQThenTwoBooks());
		commands.put(CommandName.FILL_EMPLOYEE_BOOK_TABLE, new FillEmployeeBook());
	}

	public Command getCommand(CommandName commandName) {
		Command command;
		command = commands.get(commandName);
		return command;
	}
}
