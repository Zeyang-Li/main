package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.ENDDATE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.ENDDATE_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CS2113;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalTasks.CS2101;
import static seedu.address.testutil.TypicalTasks.CS2113;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.suggestions.WrongCommandSuggestion;
import seedu.address.model.Model;
import seedu.address.model.account.Username;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Categories;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskUtil;

public class AddCommandSystemTest extends TaskBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        assertCommandSuccess("login");

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a task without tags to a non-empty task book
         * -> added
         */
        Task toAdd = CS2113;
        String command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113
                + STARTTIME_DESC_CS2113 + ENDDATE_DESC_CS2113 + ENDDATE_DESC_CS2113
                + ENDTIME_DESC_CS2113
                + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113 + TAG_DESC_CS2113;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding CS2113 to the list -> CS2113 deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add to empty task book -> added */
        deleteAllTasks();
        assertCommandSuccess(CS2113);

        /* Case: add a task with tags, command with parameters in random order -> added */
        toAdd = CS2101;
        command = AddCommand.COMMAND_WORD + TAG_DESC_CS2101 + STARTTIME_DESC_CS2101 + STARTDATE_DESC_CS2101
                + NAME_DESC_CS2101 + ENDDATE_DESC_CS2101 + ENDTIME_DESC_CS2101 + DESCRIPTION_DESC_CS2101
                + CATEGORY_DESC_CS2101;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate task -> rejected */
        command = TaskUtil.getAddCommand(CS2101);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a duplicate person except with different tags -> rejected */
        command = TaskUtil.getAddCommand(CS2101) + " " + PREFIX_TAG.getPrefix() + "others";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113 + ENDDATE_DESC_CS2113
                + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113 + TAG_DESC_CS2113;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing startDate -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTTIME_DESC_CS2113 + ENDDATE_DESC_CS2113
                + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing startTime -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + ENDDATE_DESC_CS2113
                + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing endDate -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing endTime -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + ENDTIME_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing categories -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid command -> rejected */
        command = "adds " + TaskUtil.getTaskDetails(toAdd);
        List<String> listOfCommands = new WrongCommandSuggestion().getSuggestions("adds");
        String suggestionsToString = StringUtil.join(listOfCommands, ", ");
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND + '\n'
                + String.format(WrongCommandSuggestion.SUGGESTION_HEADER, suggestionsToString));

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid startDate -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + INVALID_STARTDATE_DESC + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, StartDate.MESSAGE_CONSTRAINTS);

        /* Case: invalid startTime -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + INVALID_STARTTIME_DESC
                + ENDDATE_DESC_CS2113 + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, StartTime.MESSAGE_CONSTRAINTS);

        /* Case: invalid endDate -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + INVALID_ENDDATE_DESC + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, EndDate.MESSAGE_CONSTRAINTS);

        /* Case: invalid endTime -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + INVALID_ENDTIME_DESC + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, EndTime.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + ENDTIME_DESC_CS2113 + INVALID_DESCRIPTION_DESC + CATEGORY_DESC_CS2113;
        assertCommandFailure(command, Description.MESSAGE_CONSTRAINTS);

        /* Case: invalid categories -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + INVALID_CATEGORY_DESC;
        assertCommandFailure(command, Categories.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_CS2113 + STARTDATE_DESC_CS2113 + STARTTIME_DESC_CS2113
                + ENDDATE_DESC_CS2113 + ENDTIME_DESC_CS2113 + DESCRIPTION_DESC_CS2113 + CATEGORY_DESC_CS2113
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code TaskBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see TaskBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Task toAdd) {
        assertCommandSuccess(TaskUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Task)
     */
    private void assertCommandSuccess(String command, Task toAdd) {
        Model expectedModel = getModel();
        expectedModel.addTask(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Task)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Task)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    private void assertCommandSuccess(String command) {
        Username username = new Username("admin");
        assertCommandSuccess(TaskUtil.getLoginCommand(command), username);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Task)
     */
    private void assertCommandSuccess(String command, Username username) {
        Model expectedModel = getModel();
        expectedModel.setLoggedInUser(username);
        String expectedResultMessage = String.format(LoginCommand.MESSAGE_SUCCESS, username);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code TaskBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see TaskBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
