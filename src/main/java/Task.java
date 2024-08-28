public class Task {
    protected String description;
    protected boolean isDone;
    protected String type;

    public Task(String description, String type) {
        this.description = description;
        this.isDone = false;
        this.type= type;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void setMarkStatus(boolean status) {
        this.isDone = status;
    }

    @Override
    public String toString() {
        return this.description;
    }

}
