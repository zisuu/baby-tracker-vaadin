package ch.finecloud.babytracker.data.entity;


public enum Status {
    STARTED("Started"),
    COMPLETED("Completed"),
    INTERRUPTED("Interrupted");
    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
