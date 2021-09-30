package Model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = { "title", "priority", "expireDate", "description" })
public class Task implements Serializable {

    private String title;

    private TaskPriority priority;

    private LocalDate expireDate;

    private String description;

    public Task() {
    }

    public Task(String title, TaskPriority priority, LocalDate expireDate, String description) {
        this.title = title;
        this.priority = priority;
        this.expireDate = expireDate;
        this.description = description;
    }

    public Task(String title, TaskPriority priority, String description) {
        this.title = title;
        this.priority = priority;
        this.description = description;
        this.expireDate = LocalDate.of(1999, 12, 12);
    }

    @Override
    public String toString() {
        return this.title;
    }

    public String getTitle() {
        return title;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }
}
