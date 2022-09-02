package io.github.adainish.itemmodifiers.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.adainish.itemmodifiers.ItemModifiers;
import io.github.adainish.itemmodifiers.enumerations.ItemTypeHandler;
import io.github.adainish.itemmodifiers.enumerations.ItemTypes;
import io.github.adainish.itemmodifiers.obj.*;
import io.github.adainish.itemmodifiers.util.PermissionUtil;
import io.github.adainish.itemmodifiers.util.Util;
import io.github.adainish.itemmodifiers.wrapper.PermissionWrapper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Optional;

public class Command {
    public static LiteralArgumentBuilder <CommandSource> getCommand() {
        return Commands.literal("itemmodifiers")
                .requires(cs -> PermissionUtil.checkPermAsPlayer(cs, PermissionWrapper.adminPermission))
                .then(Commands.literal("reload")
                        .executes(cc -> {
                            ItemModifiers.instance.reload();
                            Util.send(cc.getSource(), "&cReloaded the config files and modifiers");
                            return 1;
                        })
                )
                .then(
                        Commands.argument("target", StringArgumentType.string())
                                .executes(cc -> {
                                    Optional <ServerPlayerEntity> player = Util.getPlayerOptional(StringArgumentType.getString(cc, "player"));
                                    if (player.isPresent()) {
                                        Util.send(cc.getSource(), "&cPlease provide a player name!");
                                    } else Util.send(cc.getSource(), "&cPlease provide an online player name!");
                                    return 1;
                                })
                                .then(Commands.argument("itemtype", StringArgumentType.string())
                                        .executes(context -> {
                                            String typeString = StringArgumentType.getString(context, "itemtype");
                                            if (!ItemTypeHandler.isItemType(typeString)) {
                                                Util.send(context.getSource(), "&cPlease provide a valid modifier type");
                                                return 1;
                                            }
                                            ItemTypes itemType = ItemTypeHandler.getItemType(typeString);
                                            switch (itemType) {
                                                case Ability:
                                                case EVS:
                                                case Gender:
                                                case IVS:
                                                case Level:
                                                case Nature:
                                                case PokeBall:
                                                case Shiny:
                                                case Size:
                                                    Util.send(context.getSource(), "&ePlease provide a valid item name");
                                                    break;
                                            }
                                            return 1;
                                        })
                                        .then(Commands.argument("itemname", StringArgumentType.greedyString())
                                                .executes(context -> {
                                                            try {
                                                                String typeString = StringArgumentType.getString(context, "itemtype");
                                                                String itemProvided = StringArgumentType.getString(context, "itemname");
                                                                String targetName = StringArgumentType.getString(context, "target");
                                                                ServerPlayerEntity target = Util.getPlayer(targetName);
                                                                if (target == null) {
                                                                    throw new Exception("&cPlease provide a valid online player name");
                                                                }
                                                                if (!ItemTypeHandler.isItemType(typeString)) {
                                                                    throw new Exception("&cPlease provide a valid modifier type");
                                                                }
                                                                ItemTypes itemType = ItemTypeHandler.getItemType(typeString);
                                                                switch (itemType) {
                                                                    case Ability: {
                                                                        if (Ability.isItem(itemProvided)) {
                                                                            Ability.Items item = Ability.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case EVS: {
                                                                        if (EVS.isItem(itemProvided)) {
                                                                            EVS.Items item = EVS.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Gender: {
                                                                        if (Gender.isItem(itemProvided)) {
                                                                            Gender.Items item = Gender.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case IVS: {
                                                                        if (IVS.isItem(itemProvided)) {
                                                                            IVS.Items item = IVS.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Level: {
                                                                        if (Level.isItem(itemProvided)) {
                                                                            Level.Items item = Level.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Nature: {
                                                                        if (Nature.isItem(itemProvided)) {
                                                                            Nature.Items item = Nature.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case PokeBall: {
                                                                        if (PokeBall.isItem(itemProvided)) {
                                                                            PokeBall.Items item = PokeBall.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Shiny: {
                                                                        if (Shiny.isItem(itemProvided)) {
                                                                            Shiny.Items item = Shiny.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Size: {
                                                                        if (Size.isItem(itemProvided)) {
                                                                            Size.Items item = Size.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            throw new Exception("&CThis is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                ItemModifiers.log.error(e);
                                                            }
                                                            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                                                        }
                                                )
                                        )
                                )
                );
    }
}
