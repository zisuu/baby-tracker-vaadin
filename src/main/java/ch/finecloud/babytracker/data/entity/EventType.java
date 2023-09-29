package ch.finecloud.babytracker.data.entity;

public enum EventType {
    BOTTLE_FEEDING("Bottle Feeding"),
    BREAST_FEEDING("Breast Feeding"),
    SLEEPING("Sleeping"),
    DIAPER("Diaper"),
    BEDTIME("Bedtime"),
    BATHING("Bathing"),
    CRYING("Crying");
    private final String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
