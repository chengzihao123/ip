package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.EmptyTaskException;
import exceptions.InvalidInputException;
import exceptions.TaskIndexOutOfBound;
import parser.Parser;
import storage.Storage;
import task.TaskList;

/**
 * This class is responsible for creating a ChattyBuddy instance which
 * handles user commands through the Parser, TaskList, and Storage components.
 */
public class ChattyBuddy {

    private TaskList taskList;
    private Storage storage;

    /**
     * Initializes the ChattyBuddy application, loading tasks from storage.
     */
    public ChattyBuddy() {
        storage = new Storage("./data/chattybuddy.txt");

        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (FileNotFoundException e) {
            taskList = new TaskList(new ArrayList<>());
        }
    }

    /**
     * Processes the user's input and returns a response.
     * @param input The user's input message.
     * @return The response generated by ChattyBuddy.
     */
    public String getResponse(String input) {
        if (input.equals("bye")) {
            return null;
        }

        try {
            String response = Parser.parseCommand(input, taskList, storage);
            storage.saveTasks(taskList.getTasks()); // Save tasks to storage after processing
            return response;
        } catch (InvalidInputException | EmptyTaskException | TaskIndexOutOfBound e) {
            return e.getMessage();
        } catch (IOException e) {
            return "Error: Unable to save tasks to file.";
        }
    }
}
