package model;

public interface Prioritizable {
    enum Priority {LOW, MEDIUM, HIGH}

    Priority getPriority();
    void setPriority(Priority priority);
}
