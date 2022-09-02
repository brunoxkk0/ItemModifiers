package io.github.adainish.itemmodifiers.methods;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import io.github.adainish.itemmodifiers.obj.*;
import io.github.adainish.itemmodifiers.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class PixelmonMethods {
    public static void useAbilityModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        Ability.Items abilityMod = Ability.getItem(modifier);

        if (!abilityMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!abilityMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!abilityMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }
        UIManager.openUIForcefully(pokemon.getOwnerPlayer(), GenerateUI.modifyAbility(pokemon.getOwnerPlayer(), pokemon, abilityMod, itemStack));
    }

    public static void useLevelModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        Level.Items levelMod = Level.getItem(modifier);

        if (!levelMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!levelMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!levelMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }
        UIManager.closeUI(pokemon.getOwnerPlayer());
        String action = "increased";
        if (levelMod.isIncrease())
            increasePokemonLevel(pokemon, levelMod.getLevels());
        else {
            shrinkPokemonLevel(pokemon, levelMod.getLevels());
            deleteIllegalLevelMoves(pokemon);
            action = "decreased";
        }
        itemStack.shrink(1);
        Util.send(pokemon.getOwnerPlayer(), "&eYour %pokemon%'s level has been %action% by %amount%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%action%", action).replaceAll("%amount%", String.valueOf(levelMod.getLevels())));
    }

    public static void useIVSModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        IVS.Items ivsMod = IVS.getItem(modifier);
        if (!ivsMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!ivsMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!ivsMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }
        UIManager.openUIForcefully(pokemon.getOwnerPlayer(), GenerateUI.modifyIVS(pokemon.getOwnerPlayer(), pokemon, ivsMod, itemStack));
    }

    public static void useEVSModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        EVS.Items evsMod = EVS.getItem(modifier);
        if (!evsMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!evsMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!evsMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }
        if (pokemon.getEVs().getTotal() >= 510) {
            Util.send(pokemon.getOwnerPlayer(), "&cYour %p's EVs are already maxed out".replace("%p", pokemon.getLocalizedName()));
            return;
        }
        if (( pokemon.getEVs().getTotal() + evsMod.getAmount()) > 510) {
            Util.send(pokemon.getOwnerPlayer(), "&cYour %p's EVs would be higher than the allowed amount if used!".replace("%p", pokemon.getLocalizedName()));
            return;
        }
        UIManager.openUIForcefully(pokemon.getOwnerPlayer(), GenerateUI.modifyEVS(pokemon.getOwnerPlayer(), pokemon, evsMod, itemStack));
    }

    public static void useGenderModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        Gender.Items genderMod = Gender.getItem(modifier);

        if (!genderMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!genderMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!genderMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }
        UIManager.openUIForcefully(pokemon.getOwnerPlayer(), GenerateUI.modifyGender(pokemon.getOwnerPlayer(), pokemon, genderMod, itemStack));
    }

    public static void useShinyModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        Shiny.Items shinyMod = Shiny.getItem(modifier);

        if (pokemon.isShiny()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou can't modify a Pokemon that is already Shiny!");
            return;
        }

        if (!shinyMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!shinyMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!shinyMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }
        UIManager.closeUI(pokemon.getOwnerPlayer());
        pokemon.setShiny(true);
        itemStack.shrink(1);
        Util.send(pokemon.getOwnerPlayer(), "&bYou made your %pokemon% Shiny!".replaceAll("%pokemon%", pokemon.getLocalizedName()));
    }

    public static void useSizeModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        Size.Items sizeMod = Size.getItem(modifier);

        if (!sizeMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!sizeMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!sizeMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }
        UIManager.openUIForcefully(pokemon.getOwnerPlayer(), GenerateUI.modifySize(pokemon.getOwnerPlayer(), pokemon, sizeMod, itemStack));
    }

    public static void usePokeBallModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        PokeBall.Items pokeballMod = PokeBall.getItem(modifier);

        if (!pokeballMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!pokeballMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!pokeballMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }

        UIManager.openUIForcefully(pokemon.getOwnerPlayer(), GenerateUI.modifyPokeBall(pokemon.getOwnerPlayer(), pokemon, pokeballMod, itemStack));

    }

    public static void useNatureModifier(String modifier, Pokemon pokemon, ItemStack itemStack) {
        Nature.Items natureMod = Nature.getItem(modifier);

        if (!natureMod.isAllowDitto() && pokemon.getSpecies().equals(PixelmonSpecies.DITTO.getValueUnsafe())) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify a Ditto using this Item!");
            return;
        }

        if (!natureMod.isAllowLegends() && pokemon.isLegendary()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Legendary Pokemon using this Item!");
            return;
        }

        if (!natureMod.isAllowUltraBeasts() && pokemon.isUltraBeast()) {
            Util.send(pokemon.getOwnerPlayer(), "&cYou aren't allowed to modify Ultra Beasts using this Item!");
            return;
        }
        UIManager.openUIForcefully(pokemon.getOwnerPlayer(), GenerateUI.modifyNature(pokemon.getOwnerPlayer(), pokemon, natureMod, itemStack));
    }

    public static void deleteIllegalLevelMoves(Pokemon pokemon) {
        int lvl = pokemon.getPokemonLevel();
        List <ImmutableAttack> levelUpMoves = pokemon.getForm().getMoves().getMovesAtLevel(lvl + 1);
        for (ImmutableAttack move: levelUpMoves) {
            if (pokemon.getMoveset().hasAttack(move)) {
                pokemon.getMoveset().removeAttack(move);
                pokemon.getOwnerPlayer().sendMessage(new StringTextComponent("§7Your Pokemon currently knows Moves it shouldn't at its new level, so these have been removed"), pokemon.getOwnerPlayer().getUniqueID());
            }
        }
    }

    public static void increasePokemonIVS(Pokemon pokemon, BattleStatsType statsType, Integer value) {
        int currentStat = pokemon.getIVs().getStat(statsType);
        pokemon.getIVs().setStat(statsType, currentStat + value);
    }

    public static void shrinkPokemonIVS(Pokemon pokemon, BattleStatsType statsType, Integer value) {
        int currentStat = pokemon.getIVs().getStat(statsType);
        pokemon.getIVs().setStat(statsType, currentStat - value);
    }

    public static void increasePokemonEVS(Pokemon pokemon, BattleStatsType statsType, Integer value) {
        int currentStat = pokemon.getEVs().getStat(statsType);
        pokemon.getEVs().setStat(statsType, currentStat + value);
    }

    public static void shrinkPokemonEVS(Pokemon pokemon, BattleStatsType statsType, Integer value) {
        int currentStat = pokemon.getEVs().getStat(statsType);
        pokemon.getIVs().setStat(statsType, currentStat - value);
    }

    public static void shrinkPokemonLevel(Pokemon pokemon, int lvl){
        int currentLVL = pokemon.getPokemonLevel();
        pokemon.setLevel(currentLVL - lvl);
    }

    public static void increasePokemonLevel(Pokemon pokemon, int lvl) {
        int currentLVL = pokemon.getPokemonLevel();
        pokemon.setLevel(currentLVL + lvl);
    }

    public static Integer returnPokemonIVS(PixelmonEntity pokemon, BattleStatsType statsType) {
        return pokemon.getPokemon().getIVs().getStat(statsType);
    }

    public static Integer returnPokemonEVS(PixelmonEntity pokemon, BattleStatsType statsType) {
        return pokemon.getPokemon().getEVs().getStat(statsType);
    }
}
