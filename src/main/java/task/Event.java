package task;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class Event extends Task {

    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ha");
    public Event() {
        super("", "E");
    }

    public void convertStringToTask(String[] slicedStr) {
        String taskDetails = String.join(" ",Arrays.copyOfRange(slicedStr, 1, slicedStr.length));
        String[] taskParts = taskDetails.split(" /from | /to ");
        this.description = taskParts[0];
        this.startTime = parseDateTime(taskParts[1]);
        this.endTime = parseDateTime(taskParts[2]);
    }

    public void convertSavedDataToTask(String[] dataArr) {
        this.setMarkStatus(dataArr[1].equals("1"));
        this.description = dataArr[2];
        this.startTime = parseDateTime(dataArr[3]);
        this.endTime = parseDateTime(dataArr[4]);
    }

    public LocalDateTime parseDateTime(String dateStr) {
        List<DateTimeFormatter> dateTimeFormatters = Arrays.asList(
                DateTimeFormatter.ofPattern("d MMM yyyy HHmm"),
                DateTimeFormatter.ofPattern("d MMM yyyy ha"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd ha"),
                DateTimeFormatter.ofPattern("MM/dd/yy HHmm"),
                DateTimeFormatter.ofPattern("MM/dd/yy ha"),
                DateTimeFormatter.ofPattern("MMM d yyyy HHmm"),
                DateTimeFormatter.ofPattern("MMM d yyyy ha"),
                DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"),
                DateTimeFormatter.ofPattern("MMM dd yyyy ha"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy ha"),
                DateTimeFormatter.ofPattern("dd/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("dd/M/yyyy ha"),
                DateTimeFormatter.ofPattern("d-MMMM-yyyy HHmm"),
                DateTimeFormatter.ofPattern("d-MMMM-yyyy ha"),
                DateTimeFormatter.ofPattern("d/MM/yyyy HHmm"),
                DateTimeFormatter.ofPattern("d/MM/yyyy ha"),
                DateTimeFormatter.ofPattern("d MMM HHmm"),
                DateTimeFormatter.ofPattern("d MMM ha"),
                DateTimeFormatter.ofPattern("d MMMM HHmm"),
                DateTimeFormatter.ofPattern("d MMMM ha"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        );

        List<DateTimeFormatter> dateFormatters = Arrays.asList(
                DateTimeFormatter.ofPattern("d MMM yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM/dd/yy"),
                DateTimeFormatter.ofPattern("MMM d yyyy"),
                DateTimeFormatter.ofPattern("MMM dd yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd/M/yyyy"),
                DateTimeFormatter.ofPattern("d-MMMM-yyyy"),
                DateTimeFormatter.ofPattern("d/MM/yyyy"),
                DateTimeFormatter.ofPattern("d MMM"),
                DateTimeFormatter.ofPattern("d MMMM")
        );

        // First, try parsing as LocalDateTime with a time component
        for (DateTimeFormatter formatter : dateTimeFormatters) {
            try {
                return LocalDateTime.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Continue to the next format if parsing fails
            }
        }

        // If no time component is present, try parsing as LocalDate and set the time to 00:00
        for (DateTimeFormatter formatter : dateFormatters) {
            try {
                LocalDate date = LocalDate.parse(dateStr, formatter);
                return date.atStartOfDay();
            } catch (DateTimeParseException e) {
                // Continue to the next format if parsing fails
            }
        }

        throw new IllegalArgumentException("Oops! Date format not recognized: " + dateStr + ". Have you entered the correct time?");
    }
    @Override
    public String toSavedFormat(String separation) {
        return super.toSavedFormat(separation)
                + separation + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                + separation + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    @Override
    public String toString() {
        String formattedStartTime = this.startTime.format(formatter);
        String formattedEndTime = this.endTime.format(formatter);
        return this.description + " (from: " + formattedStartTime + " to: " + formattedEndTime + ")";
    }
}

