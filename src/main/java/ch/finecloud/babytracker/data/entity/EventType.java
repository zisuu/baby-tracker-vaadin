package ch.finecloud.babytracker.data.entity;

import lombok.Getter;

@Getter
public enum EventType {
    BOTTLE_FEEDING("Bottle Feeding", "GLASS_WATER"),
    BREAST_FEEDING("Breast Feeding", "PERSON_BREASTFEEDING"),
    SLEEPING("Sleeping", "BED"),
    DIAPER("Diaper", "TOILET_PAPER"),
    BEDTIME("Bedtime", "STAR"),
    BATHING("Bathing", "BATH"),
    CRYING("Crying", "FACE_SAD_CRY");
    private final String displayName;
    private final String iconName;

    EventType(String displayName, String iconName) {
        this.displayName = displayName;
        this.iconName = iconName;
    }

}
