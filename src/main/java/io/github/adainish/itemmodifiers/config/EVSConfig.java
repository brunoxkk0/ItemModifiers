package io.github.adainish.itemmodifiers.config;

import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.itemmodifiers.ItemModifiers;

import java.util.Arrays;
import java.util.List;

public class EVSConfig extends Configurable {
    private static EVSConfig config;

    public static EVSConfig getConfig() {
        if (config == null) {
            config = new EVSConfig();
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
        List <String> statsTypes = Arrays.asList("HP",
                "Attack",
                "Defence",
                "SpecialAttack",
                "SpecialDefence",
                "Speed");
        try {
            this.get().node("Example", "Permission").set("").comment("Permission needed to use this item");
            this.get().node("Example", "ItemString").set("minecraft:paper").comment("Item we're using");
            this.get().node("Example", "Lore").set(Arrays.asList("&cOmg, An Item", "&b&lAnd it modifies my pokemons EVS!")).comment("the Lore displayed on the Item");
            this.get().node("Example", "Display").set("&dEV Transformer");
            this.get().node("Example", "Enchanted").set(false).comment("Should this item be enchanted?");
            this.get().node("Example", "Stats").set(statsTypes).comment("What Stat(s) is this allowed to modify, (Available Stats: Attack," +
                    "                Defence," +
                    "                SpecialAttack," +
                    "                SpecialDefence," +
                    "                Speed");
            this.get().node("Example", "Specs").set(Arrays.asList("unbreedable", "capped")).comment("A list of spec flags to assign to the pokemon when this item is used");
            this.get().node("Example", "AllowLegends").set(true).comment("Can this be used on Legends?");
            this.get().node("Example", "AllowUBs").set(true).comment("Can this be used on Ultra Beasts?");
            this.get().node("Example", "AllowDitto").set(true).comment("Can this be used on Ditto?");
            this.get().node("Example", "Increase").set(true).comment("If this value is true it will increase, if it is false it will decrease");
            this.get().node("Example", "Amount").set(1).comment("How many EVS are we working with?");
        } catch (SerializationException e) {
            ItemModifiers.log.error(e.getMessage());
        }

    }

    public String getConfigName() {
        return "EVSItems.conf";
    }

    public EVSConfig() {}
}
