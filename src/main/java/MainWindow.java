import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.ChattyBuddy;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private ChattyBuddy chattyBuddy;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image botImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Handles user input and bot response, updating the dialog container.
     */
    public void setChattyBuddy(ChattyBuddy chattyBuddy) {
        this.chattyBuddy = chattyBuddy;
    }

    /**
     * Handles user input and bot response, updating the dialog container.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return; // Ignore empty input
        }

        String response = chattyBuddy.getResponse(input);

        // Add user message to the dialog container
        addDialog(DialogBox.getUserDialog(input, userImage));

        // If the response is null, it indicates "bye" was entered, show goodbye and exit
        if (response == null) {
            showGoodbyeMessage();
            // Use a delay before exiting to allow the user to see the goodbye message
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }, 1500);
        } else {
            addDialog(DialogBox.getBotDialog(response, botImage));
        }

        userInput.clear();
    }

    /**
     * Adds a dialog to the dialog container.
     * @param dialog The dialog box to be added.
     */
    private void addDialog(DialogBox dialog) {
        dialogContainer.getChildren().add(dialog);
    }

    /**
     * Shows the welcome message to the user
     */
    public void showWelcomeMessage() {
        String logo = " ____        _           \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        addDialog(DialogBox.getBotDialog("Hello from\n" + logo
                                          + "\nHello! I'm ChattyBuddy\nWhat can I do for you?", botImage));
    }

    /**
     * Shows the goodbye message to the user
     */
    public void showGoodbyeMessage() {
        addDialog(DialogBox.getBotDialog("Bye. Hope to see you again soon!", botImage));
    }
}
