package io.github.adainish.itemmodifiers.config;

import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.itemmodifiers.ItemModifiers;

import java.util.Arrays;

public class ShinyConfig extends Configurable {
    private static ShinyConfig config;

    public static ShinyConfig getConfig() {
        if (config == null) {
            config = new ShinyConfig();
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
            this.get().node("Example", "Lore").set(Arrays.asList("&cOmg, An Item", "&b&lAnd it makes my Pokemon Shiny!")).comment("the Lore displayed on the Item");
            this.get().node("Example", "Display").set("&eShiny Token");
            this.get().node("Example", "Specs").set(Arrays.asList("unbreedable", "capped")).comment("A list of spec flags to assign to the pokemon when this item is used");
            this.get().node("Example", "AllowLegends").set(true).comment("Can this be used on Legends?");
            this.get().node("Example", "AllowUBs").set(true).comment("Can this be used on Ultra Beasts?");
            this.get().node("Example", "AllowDitto").set(true).comment("Can this be used on Ditto?");
        } catch (SerializationException e) {
            ItemModifiers.log.error(e.getMessage());
        }

    }

    public String getConfigName() {
        return "ShinyItems.conf";
    }

    public ShinyConfig() {}
}
