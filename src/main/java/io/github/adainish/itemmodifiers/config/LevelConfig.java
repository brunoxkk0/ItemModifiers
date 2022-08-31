package io.github.adainish.itemmodifiers.config;

import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.itemmodifiers.ItemModifiers;

import java.util.Arrays;

public class LevelConfig extends Configurable {
    private static LevelConfig config;

    public static LevelConfig getConfig() {
        if (config == null) {
            config = new LevelConfig();
        }
        return config;
    }

    public void setup() {
        super.setup();
    }

    public void load() {
        super.load();
    }

    public void populate() {
        try {
            this.get().node("Example", "Permission").set("").comment("Permission needed to use this item");
            this.get().node("Example", "ItemString").set("minecraft:paper").comment("Item we're using");
            this.get().node("Example", "Enchanted").set(false).comment("Should this item be enchanted?");
            this.get().node("Example", "Lore").set(Arrays.asList("&cOmg, An Item", "&b&lAnd it levels my pokemon up by 2 levels!")).comment("the Lore displayed on the Item");
            this.get().node("Example", "Display").set("&dSugar Candy");
            this.get().node("Example", "Specs").set(Arrays.asList("unbreedable", "capped")).comment("A list of spec flags to assign to the pokemon when this item is used");
            this.get().node("Example", "AllowLegends").set(true).comment("Can this be used on Legends?");
            this.get().node("Example", "AllowUBs").set(true).comment("Can this be used on Ultra Beasts?");
            this.get().node("Example", "AllowDitto").set(true).comment("Can this be used on Ditto?");
            this.get().node("Example", "Increase").set(true).comment("If this value is true it will increase, if it is false it will decrease");
            this.get().node("Example", "Levels").set(2).comment("How many levels are we working with?");
        } catch (SerializationException e) {
            ItemModifiers.log.error(e.getMessage());
        }
    }

    public String getConfigName() {
        return "LevelItems.conf";
    }

    public LevelConfig() {}
}
