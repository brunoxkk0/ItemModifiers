package io.github.adainish.itemmodifiers.enumerations;

public class ItemTypeHandler {
    public static boolean isItemType(String val) {
        for (ItemTypes itemTypes:ItemTypes.values()) {
            if (itemTypes.name().equalsIgnoreCase(val))
                return true;
        }
        return false;
    }

    public static ItemTypes getItemType(String val) {
        for (ItemTypes itemTypes:ItemTypes.values())
            if (itemTypes.name().equalsIgnoreCase(val))
                return itemTypes;
        return null;
    }
}
