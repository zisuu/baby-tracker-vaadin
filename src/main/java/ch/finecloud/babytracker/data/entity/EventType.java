package ch.finecloud.babytracker.data.entity;

public enum EventType {
    // path to svg icons: src/main/resources/META-INF/resources/frontend/icons
    BOTTLE_FEEDING("Bottle Feeding", "images/bottle-feeding.png"),
    BREAST_FEEDING("Breast Feeding", "images/breast-feeding.png"),
    SLEEPING("Sleeping", "images/sleeping.png"),
    DIAPER("Diaper", "images/diaper.png"),
    BEDTIME("Bedtime", "images/bedtime.png"),
    BATHING("Bathing", "images/bathing.png"),
    CRYING("Crying", "images/crying.png");
    private final String displayName;
    private final String pictureUrl;

    EventType(String displayName, String pictureUrl) {
        this.displayName = displayName;
        this.pictureUrl = pictureUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
