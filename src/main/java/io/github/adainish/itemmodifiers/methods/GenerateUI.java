package io.github.adainish.itemmodifiers.methods;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBallRegistry;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.registries.PixelmonItems;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import io.github.adainish.itemmodifiers.enumerations.ItemTypes;
import io.github.adainish.itemmodifiers.obj.*;
import io.github.adainish.itemmodifiers.util.Util;
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
                Util.send(player, "&eYour %pokemon% now has the Gender %gender%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%gender%", gender.name()));
                UIManager.closeUI(buttonAction.getPlayer());
            }).build();
            buttonList.add(button);
        }
        return buttonList;
    }

    public static List<Button> abilities(ServerPlayerEntity player, Pokemon pokemon, Ability.Items mod, ItemStack itemStack) {
        List<Button> buttonList = new ArrayList<>();

        for(com.pixelmonmod.pixelmon.api.pokemon.ability.Ability a: pokemon.getForm().getAbilities().getAll()) {
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
                Util.send(player, "&eYour %pokemon% now has the ability %ability%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%ability%", a.getName()));
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
                Util.send(player, "&eYour %pokemon% %stat% has been %action% by %amount%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%stat%", statType).replaceAll("%action%", action).replaceAll("%amount%", String.valueOf(amount)));
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
                Util.send(player, "&eYour %pokemon% %stat% has been %action% by %amount%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%stat%", statType).replaceAll("%action%", action).replaceAll("%amount%", String.valueOf(amount)));
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
                Util.send(player, "&eYour %pokemon% now has the size %size%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%size%", growth));
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
                Util.send(player, "&eYour %pokemon% now has the pokeball %ball%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%ball%", nat));
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
                Util.send(player, "&eYour %pokemon% now has the nature %nature%".replaceAll("%pokemon%", pokemon.getLocalizedName()).replaceAll("%nature%", nat));
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

    public static LinkedPage generateParty(ServerPlayerEntity player, Enum<ItemTypes> type, String itemarg, ItemStack itemStack) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());
        template.row(0, placeHolderButton);
        return PaginationHelper.createPagesFromPlaceholders(template.build(), partyPokemon(player, type, itemarg, itemStack),LinkedPage.builder()
                .template(template.build())
                .title("Modify which Pokemon?"));
    }

    public static LinkedPage modifyIVS(ServerPlayerEntity player, Pokemon pokemon, IVS.Items ivsMod, ItemStack itemStack) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());
        template.row(0, placeHolderButton);
        return PaginationHelper.createPagesFromPlaceholders(template.build(), ivs(player, pokemon, ivsMod, itemStack),LinkedPage.builder()
                .template(template.build())
                .title("Which IV Stat do you want to Edit?"));
    }

    public static LinkedPage modifyEVS(ServerPlayerEntity player, Pokemon pokemon, EVS.Items evsMod, ItemStack itemStack) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());
        template.row(0, placeHolderButton);
        return PaginationHelper.createPagesFromPlaceholders(template.build(), evs(player, pokemon, evsMod, itemStack),LinkedPage.builder()
                .template(template.build())
                .title("Which EV Stat do you want to Edit?"));
    }

    public static LinkedPage modifyGender(ServerPlayerEntity player, Pokemon pokemon, Gender.Items genderMod, ItemStack itemStack) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());
        template.row(0, placeHolderButton);
        return PaginationHelper.createPagesFromPlaceholders(template.build(), genders(player, pokemon, genderMod, itemStack),LinkedPage.builder()
                .template(template.build())
                .title("Which Gender do you want?"));
    }

    public static LinkedPage modifySize(ServerPlayerEntity player, Pokemon pokemon, Size.Items sizeMod, ItemStack itemStack) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        ChestTemplate.Builder template = ChestTemplate.builder(4).fill(GooeyButton.builder().display(pane1).build());
        GooeyButton nextpage;
        GooeyButton previouspage;
        if (sizeMod.getGrowths().size() > 8) {
            nextpage = LinkedPageButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_right))
                    .title("§cNext page")
                    .linkType(LinkType.Next)
                    .build();
            previouspage = LinkedPageButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_left))
                    .title("§cPrevious page")
                    .linkType(LinkType.Previous)
                    .build();
        } else {
            nextpage = filler();
            previouspage = filler();
        }
        template.set(1, 0, previouspage);
        template.set(1, 8, nextpage);
        template.rectangle(1, 1, 2, 7, placeHolderButton);

        return PaginationHelper.createPagesFromPlaceholders(template.build(), sizes(player, pokemon, sizeMod, itemStack), LinkedPage.builder()
                .template(template.build())
                .title("Which Growth do you want?"));
    }

    public static LinkedPage modifyPokeBall(ServerPlayerEntity player, Pokemon pokemon, PokeBall.Items ball, ItemStack itemStack) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        ChestTemplate.Builder template = ChestTemplate.builder(4).fill(GooeyButton.builder().display(pane1).build());
        GooeyButton nextpage;
        GooeyButton previouspage;
        if (ball.getPokeballList().size() > 8) {
            nextpage = LinkedPageButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_right))
                    .title("§cNext page")
                    .linkType(LinkType.Next)
                    .build();
            previouspage = LinkedPageButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_left))
                    .title("§cPrevious page")
                    .linkType(LinkType.Previous)
                    .build();
        } else {
            nextpage = filler();
            previouspage = filler();
        }

        template.set(1, 0, previouspage);
        template.set(1, 8, nextpage);
        template.rectangle(1, 1, 2, 7, placeHolderButton);
        return PaginationHelper.createPagesFromPlaceholders(template.build(), pokeballs(player, pokemon, ball, itemStack),LinkedPage.builder()
                .template(template.build())
                .title("Which Ball do you want?"));
    }

    public static LinkedPage modifyNature(ServerPlayerEntity player, Pokemon pokemon, Nature.Items natureMod, ItemStack itemStack) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        ChestTemplate.Builder template = ChestTemplate.builder(4).fill(GooeyButton.builder().display(pane1).build());
        GooeyButton nextpage;
        GooeyButton previouspage;
        if (natureMod.getEnumNatures().size() > 8) {
            nextpage = LinkedPageButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_right))
                    .title("§cNext page")
                    .linkType(LinkType.Next)
                    .build();
            previouspage = LinkedPageButton.builder()
                    .display(new ItemStack(PixelmonItems.trade_holder_left))
                    .title("§cPrevious page")
                    .linkType(LinkType.Previous)
                    .build();
        } else {
            nextpage = filler();
            previouspage = filler();
        }
        template.rectangle(1, 1, 2, 7, placeHolderButton);
        template.set(1, 0, previouspage);
        template.set(1, 8, nextpage);

        return PaginationHelper.createPagesFromPlaceholders(template.build(), natures(player, pokemon, natureMod, itemStack),LinkedPage.builder()
                .template(template.build())
                .title("Which Nature do you want?"));
    }

    public static LinkedPage modifyAbility(ServerPlayerEntity player, Pokemon pokemon, Ability.Items abilityMod, ItemStack itemStack) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();
        ChestTemplate.Builder template = ChestTemplate.builder(1).fill(GooeyButton.builder().display(pane1).build());
        template.row(0, placeHolderButton);
        return PaginationHelper.createPagesFromPlaceholders(template.build(), abilities(player, pokemon, abilityMod, itemStack),LinkedPage.builder()
                .template(template.build())
                .title("Which Ability do you want?"));
    }
}
