package io.github.adainish.itemmodifiers.listeners;

import ca.landonjw.gooeylibs2.api.UIManager;
import io.github.adainish.itemmodifiers.enumerations.ItemTypes;
import io.github.adainish.itemmodifiers.methods.GenerateUI;
import io.github.adainish.itemmodifiers.obj.*;
import io.github.adainish.itemmodifiers.util.PermissionUtil;
import io.github.adainish.itemmodifiers.util.ServerUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModifierListener {

    public String argType(ItemTypes itemType) {
        String arg = null;
        switch (itemType) {
            case Ability: {
                arg = "AbilityModifier";
                break;
            }
            case EVS: {
                arg = "EVSModifier";
                break;
            }
            case Gender: {
                arg = "GenderModifier";
                break;
            }
            case IVS: {
                arg = "IVSModifier";
                break;
            }
            case Level: {
                arg = "LevelModifier";
                break;
            }
            case Nature: {
                arg = "NatureModifier";
                break;
            }
            case PokeBall: {
                arg = "PokeBallModifier";
                break;
            }
            case Shiny: {
                arg = "ShinyModifier";
                break;
            }
            case Size: {
                arg = "SizeModifier";
                break;
            }
        }

        return arg;
    }
    @SubscribeEvent
    public void onModificationConsumption(PlayerInteractEvent.RightClickItem event) {
        if (event.isCanceled())
            return;
        if (!event.isCanceled()) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            ItemTypes itemType;
            String itemArg;
            ItemStack stack = event.getItemStack();
            if (event.getHand().equals(Hand.MAIN_HAND) && stack.hasTag() && stack.getTag().getBoolean("itemmodifier")) {
                event.setCanceled(true);
                itemType = ItemTypes.valueOf(stack.getTag().getString("itemType"));
                itemArg = stack.getTag().getString(argType(itemType));
                if (itemArg.isEmpty()) {
                    System.out.println("There was an issue using a modifier item, itemArg Null! Contact the dev!");
                    return;
                }

                String perm = null;
                switch (itemType) {
                    case EVS: {
                        EVS.Items mod = EVS.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                    case IVS: {
                        IVS.Items mod = IVS.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                    case Size: {
                        Size.Items mod = Size.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                    case Level: {
                        Level.Items mod = Level.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                    case Shiny: {
                        Shiny.Items mod = Shiny.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                    case Nature: {
                        Nature.Items mod = Nature.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                    case Gender: {
                        Gender.Items mod = Gender.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                    case Ability: {
                        Ability.Items mod = Ability.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                    case PokeBall: {
                        PokeBall.Items mod = PokeBall.getItem(itemArg);
                        if (mod == null)
                            break;
                        perm = mod.getPermission();
                        break;
                    }
                }
                if (PermissionUtil.checkPerm(player, perm)){
                    UIManager.openUIForcefully(player, GenerateUI.generateParty(player, itemType, itemArg, stack));
                }
                else ServerUtil.send(player, "&cYou're not allowed to use this Item!");
            }
        }
    }
}
