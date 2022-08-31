package io.github.adainish.itemmodifiers.methods;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBallRegistry;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.registries.PixelmonItems;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.items.MintItem;
import io.github.adainish.itemmodifiers.enumerations.ItemTypes;
import io.github.adainish.itemmodifiers.obj.*;
import io.github.adainish.itemmodifiers.util.ServerUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateUI {
    private static GooeyButton filler() {
        return GooeyButton.builder()
                .display(new ItemStack(Items.GRAY_STAINED_GLASS_PANE))
                .title("")
                .build();
    }

    public static List <Button> genders(ServerPlayerEntity player, Pokemon pokemon, Gender.Items mod, ItemStack stack) {
        List<Button> buttonList = new ArrayList <>();

        for (com.pixelmonmod.pixelmon.api.pokemon.species.gender.Gender gender: com.pixelmonmod.pixelmon.api.pokemon.species.gender.Gender.values()) {

            if (pokemon.getGender() == gender)
                continue;

            if (gender.equals(com.pixelmonmod.pixelmon.api.pokemon.species.gender.Gender.NONE))
                continue;

            Button button = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("%gender%".replaceAll("%gender%", gender.name())).lore(Arrays.asList("Click to apply this Gender!")).onClick(buttonAction -> {
                pokemon.setGender(gender);
                stack.shrink(1);
                if (!mod.getSpecFlags().isEmpty()) {
                    for (String s : mod.getSpecFlags()) {
                        pokemon.addFlag(s);
                    }
                }
                ServerUtil.send(player, "&eYour %pokemon% now has the Gender %gender%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%gender%", gender.name()));
                UIManager.closeUI(buttonAction.getPlayer());
            }).build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static List<Button> abilities(ServerPlayerEntity player, Pokemon pokemon, Ability.Items mod, ItemStack itemStack) {
        List<Button> buttonList = new ArrayList<>();

        for(com.pixelmonmod.pixelmon.api.pokemon.ability.Ability a: pokemon.getForm().getAbilities().getAbilities()) {
            if (pokemon.getAbility().equals(a))
                continue;

            if (!mod.isAllowUltraBeasts() && pokemon.isUltraBeast())
                continue;


            if (!mod.isAllowLegends() && pokemon.isLegendary())
                continue;


            if (!mod.isAllowDitto() && pokemon.getSpecies().is(PixelmonSpecies.DITTO.getValueUnsafe()))
                continue;

            if (!mod.isAllowHA() && pokemon.getForm().getAbilities().isHiddenAbility(a)) {
                System.out.println("HA Detected & Not allowed, Skipping!");
                continue;
            }

            Button button = GooeyButton.builder().display(new ItemStack(PixelmonItems.ability_capsule)).title("%ability%".replaceAll("%ability%", a.getName())).lore(Arrays.asList("Click to apply this ability!")).onClick(buttonAction -> {
                pokemon.setAbility(a);
                itemStack.shrink(1);
                if (!mod.getSpecFlags().isEmpty()) {
                    for (String s : mod.getSpecFlags()) {
                        pokemon.addFlag(s);
                    }
                }
                ServerUtil.send(player, "&eYour %pokemon% now has the ability %ability%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%ability%", a.getName()));
                UIManager.closeUI(buttonAction.getPlayer());
            }).build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static List<Button> ivs(ServerPlayerEntity player, Pokemon pokemon, IVS.Items mod, ItemStack itemStack) {
        List<Button> buttonList = new ArrayList<>();
        for (String statType: mod.getStatsTypes()) {
            if (!BattleStatsType.isStatsEffect(statType))
                continue;

            if (!mod.isAllowUltraBeasts() && pokemon.isUltraBeast())
                continue;


            if (!mod.isAllowLegends() && pokemon.isLegendary())
                continue;


            if (!mod.isAllowDitto() && pokemon.getSpecies().is(PixelmonSpecies.DITTO.getValueUnsafe()))
                continue;

            Button button = GooeyButton.builder().title(statType).display(new ItemStack(PixelmonItems.protein)).onClick(buttonAction -> {
                String action = "increased";
                int amount = mod.getAmount();
                if (mod.isIncrease()) {
                    PixelmonMethods.increasePokemonIVS(pokemon, BattleStatsType.getStatsEffect(statType), amount);
                }
                else {
                    action = "decreased";
                    PixelmonMethods.shrinkPokemonIVS(pokemon, BattleStatsType.getStatsEffect(statType), amount);
                }
                if (!mod.getSpecFlags().isEmpty()) {
                    for (String s : mod.getSpecFlags()) {
                        pokemon.addFlag(s);
                    }
                }
                UIManager.closeUI(buttonAction.getPlayer());
                ServerUtil.send(player, "&eYour %pokemon% %stat% has been %action% by %amount%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%stat%", statType).replaceAll("%action%", action).replaceAll("%amount%", String.valueOf(amount)));
                itemStack.shrink(1);
            }).build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static List<Button> evs(ServerPlayerEntity player, Pokemon pokemon, EVS.Items mod, ItemStack itemStack) {
        List<Button> buttonList = new ArrayList<>();
        for (String statType: mod.getStatsTypes()) {
            if (!BattleStatsType.isStatsEffect(statType))
                continue;

            if (!mod.isAllowUltraBeasts() && pokemon.isUltraBeast())
                continue;


            if (!mod.isAllowLegends() && pokemon.isLegendary())
                continue;


            if (!mod.isAllowDitto() && pokemon.getSpecies().is(PixelmonSpecies.DITTO.getValueUnsafe()))
                continue;

            Button button = GooeyButton.builder().title(statType).display(new ItemStack(PixelmonItems.max_elixir)).onClick(buttonAction -> {
                String action = "increased";
                int amount = mod.getAmount();
                if (mod.isIncrease()) {
                    PixelmonMethods.increasePokemonEVS(pokemon, BattleStatsType.getStatsEffect(statType), amount);
                }
                else {
                    action = "decreased";
                    PixelmonMethods.shrinkPokemonEVS(pokemon, BattleStatsType.getStatsEffect(statType), amount);
                }
                if (!mod.getSpecFlags().isEmpty()) {
                    for (String s : mod.getSpecFlags()) {
                        pokemon.addFlag(s);
                    }
                }
                UIManager.closeUI(buttonAction.getPlayer());
                ServerUtil.send(player, "&eYour %pokemon% %stat% has been %action% by %amount%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%stat%", statType).replaceAll("%action%", action).replaceAll("%amount%", String.valueOf(amount)));
                itemStack.shrink(1);
            }).build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static List<Button> sizes(ServerPlayerEntity player, Pokemon pokemon, Size.Items mod, ItemStack itemStack) {
        List<Button> buttonList = new ArrayList<>();
        for (String growth: mod.getGrowths()) {

            if (!EnumGrowth.hasGrowth(growth))
                continue;

            if (!mod.isAllowUltraBeasts() && pokemon.isUltraBeast())
                continue;


            if (!mod.isAllowLegends() && pokemon.isLegendary())
                continue;


            if (!mod.isAllowDitto() && pokemon.getSpecies().is(PixelmonSpecies.DITTO.getValueUnsafe()))
                continue;

            Button button = GooeyButton.builder().display(new ItemStack(PixelmonItems.rare_bone)).title(growth).onClick(buttonAction -> {
                pokemon.setGrowth(EnumGrowth.getGrowthFromString(growth));
                itemStack.shrink(1);
                if (!mod.getSpecFlags().isEmpty()) {
                    for (String s : mod.getSpecFlags()) {
                        pokemon.addFlag(s);
                    }
                }
                ServerUtil.send(player, "&eYour %pokemon% now has the size %size%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%size%", growth));
                UIManager.closeUI(buttonAction.getPlayer());
            }).build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static ItemStack getPokeBall(com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBall pokeBall) {
        Item item = pokeBall.getBallItem();
        return new ItemStack(item);
    }

    public static List<Button> pokeballs(ServerPlayerEntity player, Pokemon pokemon, PokeBall.Items mod, ItemStack itemStack) {
        List<Button> buttonList = new ArrayList<>();
        for (String nat: mod.getPokeballList()) {

            if (!mod.isAllowUltraBeasts() && pokemon.isUltraBeast())
                continue;


            if (!mod.isAllowLegends() && pokemon.isLegendary())
                continue;


            if (!mod.isAllowDitto() && pokemon.getSpecies().is(PixelmonSpecies.DITTO.getValueUnsafe()))
                continue;

            if (nat == null)
                continue;

            if (!PokeBallRegistry.getPokeBall(nat).isPresent())
                continue;

            com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBall pokeBall = PokeBallRegistry.getPokeBall(nat).get();

            Button button = GooeyButton.builder().display(getPokeBall(pokeBall)).title(nat).onClick(buttonAction -> {
                pokemon.setBall(pokeBall);
                itemStack.shrink(1);
                if (!mod.getSpecFlags().isEmpty()) {
                    for (String s : mod.getSpecFlags()) {
                        pokemon.addFlag(s);
                    }
                }
                ServerUtil.send(player, "&eYour %pokemon% now has the pokeball %ball%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%ball%", nat));
                UIManager.closeUI(player);
            }).build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static List<Button> natures(ServerPlayerEntity player, Pokemon pokemon, Nature.Items mod, ItemStack itemStack) {
        List<Button> buttonList = new ArrayList<>();
        for (String nat: mod.getEnumNatures()) {

            if (!com.pixelmonmod.pixelmon.api.pokemon.Nature.hasNature(nat))
                continue;

            if (!mod.isAllowUltraBeasts() && pokemon.isUltraBeast())
                continue;


            if (!mod.isAllowLegends() && pokemon.isLegendary())
                continue;


            if (!mod.isAllowDitto() && pokemon.getSpecies().is(PixelmonSpecies.DITTO.getValueUnsafe()))
                continue;



            Button button = GooeyButton.builder().display(new ItemStack(PixelmonItems.mint_serious)).title(nat).onClick(buttonAction -> {
                pokemon.setNature(com.pixelmonmod.pixelmon.api.pokemon.Nature.natureFromString(nat));
                itemStack.shrink(1);
                if (!mod.getSpecFlags().isEmpty()) {
                    for (String s : mod.getSpecFlags()) {
                        pokemon.addFlag(s);
                    }
                }
                ServerUtil.send(player, "&eYour %pokemon% now has the nature %nature%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%nature%", nat));
                UIManager.closeUI(buttonAction.getPlayer());
            }).build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static List<Button> partyPokemon(ServerPlayerEntity player, Enum<ItemTypes> type, String itemarg, ItemStack itemStack) {
        List<Button> buttonList = new ArrayList<>();
        PlayerPartyStorage pps = StorageProxy.getParty(player);

        for (Pokemon pokemon:pps.getAll()) {

            if (pokemon == null) {
                Button button = GooeyButton.builder().display(pane1).build();
                buttonList.add(button);
                continue;
            }

            if (pokemon.isEgg()) {
                Button button = GooeyButton.builder().display(pane1).build();
                buttonList.add(button);
                continue;
            }

            Button button = GooeyButton.builder().display(ItemStackMethods.getPokemonPhoto(pokemon)).title(pokemon.getDisplayName()).onClick(buttonAction -> {

                switch (type.name()) {
                    case "Ability": {
                        PixelmonMethods.useAbilityModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                    case "EVS": {
                        PixelmonMethods.useEVSModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                    case "IVS": {
                        PixelmonMethods.useIVSModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                    case "Level": {
                        PixelmonMethods.useLevelModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                    case "Nature": {
                        PixelmonMethods.useNatureModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                    case "Shiny": {
                        PixelmonMethods.useShinyModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                    case "PokeBall": {
                        PixelmonMethods.usePokeBallModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                    case "Size": {
                        PixelmonMethods.useSizeModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                    case "Gender": {
                        PixelmonMethods.useGenderModifier(itemarg, pokemon, itemStack);
                        break;
                    }
                }
            })
                    .build();
            buttonList.add(button);

        }

        return buttonList;
    }

    private static final ItemStack pane1 = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);

    public static Page generateParty(ServerPlayerEntity player, Enum<ItemTypes> type, String itemarg, ItemStack itemStack) {

        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());

        return GooeyPage.builder()
                .template(template.build())
//                .dynamicContents(partyPokemon(player, type, itemarg, itemStack))
//                .dynamicContentArea(0, 1, 1, 6)
                .title("Modify which Pokemon?")
                .build();
    }

    public static Page modifyIVS(ServerPlayerEntity player, Pokemon pokemon, IVS.Items ivsMod, ItemStack itemStack) {

        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());

        return GooeyPage.builder()
                .template(template.build())
//                .dynamicContents(ivs(player, pokemon, ivsMod, itemStack))
//                .dynamicContentArea(0, 1, 1, 6)
                .title("Which IV Stat do you want to Edit?")
                .build();
    }

    public static Page modifyEVS(ServerPlayerEntity player, Pokemon pokemon, EVS.Items evsMod, ItemStack itemStack) {

        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());

        return GooeyPage.builder()
                .template(template.build())
//                .dynamicContents(evs(player, pokemon, evsMod, itemStack))
//                .dynamicContentArea(0, 1, 1, 6)
                .title("Which EV Stat do you want to Edit?")
                .build();
    }

    public static Page modifyGender(ServerPlayerEntity player, Pokemon pokemon, Gender.Items genderMod, ItemStack itemStack) {

        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());

        return GooeyPage.builder()
                .template(template.build())
//                .dynamicContents(genders(player, pokemon, genderMod, itemStack))
//                .dynamicContentArea(0, 1, 1, 6)
                .title("Which Gender do you want?")
                .build();
    }

    public static Page modifySize(ServerPlayerEntity player, Pokemon pokemon, Size.Items sizeMod, ItemStack itemStack) {

        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());
        GooeyButton nextpage;
        GooeyButton previouspage;
        if (sizeMod.getGrowths().size() > 8) {
            nextpage = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_right))
                    .title("§cNext page")
                    .build();
            previouspage = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_left))
                    .title("§cPrevious page")
                    .build();
        } else {
            nextpage = filler();
            previouspage = filler();
        }
        template.set(0, 0, previouspage);
        template.set(0, 8, nextpage);

        return GooeyPage.builder()
                .template(template.build())
//                .dynamicContents(sizes(player, pokemon, sizeMod, itemStack))
//                .dynamicContentArea(0, 1, 1, 7)
                .title("Which Growth do you want?")
                .build();
    }

    public static Page modifyPokeBall(ServerPlayerEntity player, Pokemon pokemon, PokeBall.Items ball, ItemStack itemStack) {

        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());
        GooeyButton nextpage;
        GooeyButton previouspage;
        if (ball.getPokeballList().size() > 8) {
            nextpage = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_right))
                    .title("§cNext page")
                    .build();
            previouspage = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_left))
                    .title("§cPrevious page")
                    .build();
        } else {
            nextpage = filler();
            previouspage = filler();
        }

        template.set(0, 0, previouspage);
        template.set(0, 8, nextpage);

        return GooeyPage.builder()
                .template(template.build())
//                .dynamicContents(pokeballs(player, pokemon, ball, itemStack))
//                .dynamicContentArea(0, 1, 1, 7)
                .title("Which Ball do you want?")
                .build();
    }

    public static Page modifyNature(ServerPlayerEntity player, Pokemon pokemon, Nature.Items natureMod, ItemStack itemStack) {

        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());
        GooeyButton nextpage;
        GooeyButton previouspage;
        if (natureMod.getEnumNatures().size() > 8) {
            nextpage = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_right))
                    .title("§cNext page")
                    .build();
            previouspage = GooeyButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_left))
                    .title("§cPrevious page")
                    .build();
        } else {
            nextpage = filler();
            previouspage = filler();
        }

        template.set(0, 0, previouspage);
        template.set(0, 8, nextpage);

        return GooeyPage.builder()
                .template(template.build())
//                .dynamicContents(natures(player, pokemon, natureMod, itemStack))
//                .dynamicContentArea(0, 1, 1, 7)
                .title("Which Nature do you want?")
                .build();
    }

    public static Page modifyAbility(ServerPlayerEntity player, Pokemon pokemon, Ability.Items abilityMod, ItemStack itemStack) {

        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());

        return GooeyPage.builder()
                .template(template.build())
//                .dynamicContents(abilities(player, pokemon, abilityMod, itemStack))
//                .dynamicContentArea(0, 1, 1, 6)
                .title("Which Ability do you want?")
                .build();
    }
}
