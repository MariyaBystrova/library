package com.epam.library.command;

import com.epam.library.bean.Request;
import com.epam.library.bean.Response;

public interface Command {
	Response execute(Request request);
}
