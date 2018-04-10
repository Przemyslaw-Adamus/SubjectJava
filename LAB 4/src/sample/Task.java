package sample;

import javafx.scene.control.ListCell;
import java.io.Serializable;
import java.time.LocalDate;

public class Task extends ListCell<Task> implements Serializable{
    String title;
    String description;
    String priorityStatus;
    LocalDate deadlineDate;

    public Task() {
        this.title = "";
        this.description = "";
        this.priorityStatus = "";
        this.deadlineDate = null;
    }

    public Task(String title, String description, String priorityStatus, LocalDate deadlineDate) {
        this.title = title;
        this.description = description;
        this.priorityStatus = priorityStatus;
        this.deadlineDate = deadlineDate;

    }

    @Override
    public String toString() {
        return this.title;
    }

//    public void print() {
//        System.out.println(this.title);
//        System.out.println(this.priorityStatus);
//        System.out.println(this.deadlineDate.getYear()+"-"+this.deadlineDate.getMonth()+"-"+this.deadlineDate.getDayOfMonth());
//        System.out.println(this.description);
//    }

    public void setTask(String title, String description, String priorityStatus, LocalDate deadlineDate) {
        setTitle(title);
        setDescription(description);
        setPriorityStatus(priorityStatus);
        setDeadlineDate(deadlineDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriorityStatus() {
        return priorityStatus;
    }

    public void setPriorityStatus(String priorityStatus) {
        this.priorityStatus = priorityStatus;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}
