package io.github.adainish.itemmodifiers.config;

import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.itemmodifiers.ItemModifiers;

import java.util.Arrays;
import java.util.List;

public class NatureConfig extends Configurable {
    private static NatureConfig config;

    public static NatureConfig getConfig() {
        if (config == null) {
            config = new NatureConfig();
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
        List <String> enumNatures = Arrays.asList("Adamant", "Bashful", "Bold", "Brave", "Calm", "Careful", "Docile", "Gentle", "Hardy", "Hasty", "Impish", "Jolly", "Lax", "Lonely", "Mild", "Modest", "Naive", "Naughty", "Quiet", "Quirky", "Rash", "Relaxed", "Sassy", "Serious", "Timid");
        try {
            this.get().node("Example", "Permission").set("").comment("Permission needed to use this item");
            this.get().node("Example", "ItemString").set("minecraft:paper").comment("Item we're using");
            this.get().node("Example", "Enchanted").set(false).comment("Should this item be enchanted?");
            this.get().node("Example", "Lore").set(Arrays.asList("&cOmg, An Item", "&b&lAnd it changes my pokemon nature!")).comment("the Lore displayed on the Item");
            this.get().node("Example", "Display").set("&cNatures Blessing");
            this.get().node("Example", "Specs").set(Arrays.asList("unbreedable", "capped")).comment("A list of spec flags to assign to the pokemon when this item is used");
            this.get().node("Example", "AllowLegends").set(true).comment("Can this be used on Legends?");
            this.get().node("Example", "AllowUBs").set(true).comment("Can this be used on Ultra Beasts?");
            this.get().node("Example", "AllowDitto").set(true).comment("Can this be used on Ditto?");
            this.get().node("Example", "Natures").set(enumNatures).comment("What natures are available for this item? List of Natures: Adamant, Bashful, Bold, Brave, Calm, Careful, Docile, Gentle, Hardy, Hasty, Impish, Jolly, Lax, Lonely, Mild, Modest, Naive, Naughty, Quiet, Quirky, Rash, Relaxed, Sassy, Serious, Timid");
        } catch (SerializationException e) {
            ItemModifiers.log.error(e.getMessage());
        }
    }

    public String getConfigName() {
        return "NatureItems.conf";
    }

    public NatureConfig() {}
}
