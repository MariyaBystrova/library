package com.epam.library.bean;

import java.util.HashMap;
import java.util.Map;

import com.epam.library.command.CommandName;

public class Request {
	private CommandName commandName;
	private Map<String, Object> parameter = new HashMap<>();

	public CommandName getCommandName() {
		return commandName;
	}

	public void setCommandName(CommandName commandName) {
		this.commandName = commandName;
	}

	public Object getParameter(String key) {
		return parameter.get(key);
	}

	public void setParameter(String key, Object value) {
		this.parameter.put(key, value);
	}
}
