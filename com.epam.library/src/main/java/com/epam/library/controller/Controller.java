package com.epam.library.controller;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;
import com.epam.library.command.Command;
import com.epam.library.command.CommandName;

public class Controller {
	private final CommandProvider provider = new CommandProvider();

	public Response doAction(Request request) {
		CommandName commandName = request.getCommandName();

		Command command = provider.getCommand(commandName);

		Response response = command.execute(request);

		return response;

	}
}
