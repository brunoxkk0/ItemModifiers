package io.github.adainish.itemmodifiers.config;

import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.itemmodifiers.ItemModifiers;

import java.util.Arrays;
import java.util.List;

public class PokeBallConfig extends Configurable {
    private static PokeBallConfig config;

    public static PokeBallConfig getConfig() {
        if (config == null) {
            config = new PokeBallConfig();
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
        List <String> pokeballList = Arrays.asList("poke_ball",
                "great_ball",
                "ultra_ball",
                "master_ball",
                "level_ball",
                "moon_ball",
                "friend_ball",
                "love_ball",
                "safari_ball",
                "heavy_ball",
                "fast_ball",
                "repeat_ball",
                "timer_ball",
                "nest_ball",
                "net_ball",
                "dive_ball",
                "luxury_ball",
                "heal_ball",
                "dusk_ball",
                "premier_ball",
                "sport_ball",
                "quick_ball",
                "lure_ball",
                "park_ball",
                "cherish_ball",
                "gs_ball",
                "beast_ball",
                "dream_ball");
        try {
            this.get().node("Example", "Permission").set("").comment("Permission needed to use this item");
            this.get().node("Example", "ItemString").set("minecraft:paper").comment("Item we're using");
            this.get().node("Example", "Enchanted").set(false).comment("Should this item be enchanted?");
            this.get().node("Example", "Lore").set(Arrays.asList("&cOmg, An Item", "&b&lAnd it changes my pokemons pokeball!")).comment("the Lore displayed on the Item");
            this.get().node("Example", "Display").set("&6Kurts Paper");
            this.get().node("Example", "Specs").set(Arrays.asList("unbreedable", "capped")).comment("A list of spec flags to assign to the pokemon when this item is used");
            this.get().node("Example", "AllowLegends").set(true).comment("Can this be used on Legends?");
            this.get().node("Example", "AllowUBs").set(true).comment("Can this be used on Ultra Beasts?");
            this.get().node("Example", "AllowDitto").set(true).comment("Can this be used on Ditto?");
            this.get().node("Example", "PokeBalls").set(pokeballList).comment("What pokeballs are available for this item? List of Natures: poke_ball," +
                    "    great_ball," +
                    "    ultra_ball," +
                    "    master_ball," +
                    "    level_ball," +
                    "    moon_ball," +
                    "    friend_ball," +
                    "    love_ball," +
                    "    safari_ball," +
                    "    heavy_ball," +
                    "    fast_ball," +
                    "    repeat_ball," +
                    "    timer_ball," +
                    "    nest_ball," +
                    "    net_ball," +
                    "    dive_ball," +
                    "    luxury_ball," +
                    "    heal_ball," +
                    "    dusk_ball," +
                    "    premier_ball," +
                    "    sport_ball," +
                    "    quick_ball," +
                    "    lure_ball," +
                    "    park_ball," +
                    "    cherish_ball," +
                    "    gs_ball," +
                    "    beast_ball," +
                    "    dream_ball");
        } catch (SerializationException e) {
            ItemModifiers.log.error(e.getMessage());
        }
    }

    public String getConfigName() {
        return "PokeBallItems.conf";
    }

    public PokeBallConfig() {}
}
