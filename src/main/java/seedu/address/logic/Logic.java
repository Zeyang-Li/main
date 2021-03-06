package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.day.Day;
import seedu.address.model.task.Task;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws DataConversionException
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText)
            throws CommandException, IllegalValueException, IOException, DataConversionException;

    /**
     * Returns the TaskBook.
     *
     * @see seedu.address.model.Model#getTaskBook()
     */
    ReadOnlyTaskBook getTaskBook();

    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getFilteredTaskList();

    /** Returns an unmodifiable view of the list of tasks */
    ObservableList<Task> getRemindTaskList();

    /** reinitializeRemindList */
    void reinitializeRemindList();

    /** Returns an unmodifiable view of the filtered list of days */
    ObservableList<Day> getFilteredDayList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' task book file path.
     */
    Path getTaskBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Set the month on calendar.
     * @param month
     */
    void setMonth(String month);

    /**
     * Selected task in the filtered task list.
     * null if no task is selected.
     * @see seedu.address.model.Model#selectedTaskProperty()
     */
    ReadOnlyProperty<Task> selectedTaskProperty();

    /**
     * Selected day in the filtered day list.
     * null if no day is selected.
     *
     * @see seedu.address.model.Model#selectedDayProperty()
     */
    ReadOnlyProperty<Day> selectedDayProperty();

    /**
     * Sets the selected task in the filtered task list.
     *
     * @see seedu.address.model.Model#setSelectedTask(Task)
     */
    void setSelectedTask(Task task);

    /**
     * Sets the selected day in the filtered day list.
     *
     * @see seedu.address.model.Model#setSelectedDay(Day)
     */
    void setSelectedDay(Day day);
}
