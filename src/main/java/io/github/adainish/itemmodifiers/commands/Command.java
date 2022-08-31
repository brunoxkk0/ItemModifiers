package io.github.adainish.itemmodifiers.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.adainish.itemmodifiers.ItemModifiers;
import io.github.adainish.itemmodifiers.enumerations.ItemTypes;
import io.github.adainish.itemmodifiers.obj.*;
import io.github.adainish.itemmodifiers.util.PermissionUtil;
import io.github.adainish.itemmodifiers.util.ServerUtil;
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
                            return 1;
                        })
                )
                .then(
                        Commands.argument("target", StringArgumentType.string())
                                .executes(cc -> {
                                    Optional <ServerPlayerEntity> player = ServerUtil.getPlayerOptional(StringArgumentType.getString(cc, "player"));
                                    if (player.isPresent()) {
                                        ServerUtil.send(cc.getSource(), "&cPlease provide a player name!");
                                    } else ServerUtil.send(cc.getSource(), "&cPlease provide an online player name!");
                                    return 1;
                                })
                                .then(Commands.argument("", StringArgumentType.string())
                                        .executes(cc -> {

                                            return 1;
                                        })
                                        .then(Commands.argument("itemname", StringArgumentType.greedyString())
                                                .executes(context -> {
                                                            try {
                                                                String typeString = StringArgumentType.getString(context, "itemtype");
                                                                String itemProvided = StringArgumentType.getString(context, "itemname");
                                                                String targetName = StringArgumentType.getString(context, "target");
                                                                ServerPlayerEntity target = ServerUtil.getPlayer(targetName);
                                                                if (target == null) {
                                                                    ServerUtil.send(context.getSource(), "&cPlease provide a valid online player name");
                                                                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                                                                }
                                                                ItemTypes itemType = ItemTypes.valueOf(typeString);
                                                                switch (itemType) {
                                                                    case Ability: {
                                                                        if (Ability.isItem(itemProvided)) {
                                                                            Ability.Items item = Ability.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case EVS: {
                                                                        if (EVS.isItem(itemProvided)) {
                                                                            EVS.Items item = EVS.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Gender: {
                                                                        if (Gender.isItem(itemProvided)) {
                                                                            Gender.Items item = Gender.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case IVS: {
                                                                        if (IVS.isItem(itemProvided)) {
                                                                            IVS.Items item = IVS.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Level: {
                                                                        if (Level.isItem(itemProvided)) {
                                                                            Level.Items item = Level.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Nature: {
                                                                        if (Nature.isItem(itemProvided)) {
                                                                            Nature.Items item = Nature.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case PokeBall: {
                                                                        if (PokeBall.isItem(itemProvided)) {
                                                                            PokeBall.Items item = PokeBall.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Shiny: {
                                                                        if (Shiny.isItem(itemProvided)) {
                                                                            Shiny.Items item = Shiny.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                    case Size: {
                                                                        if (Size.isItem(itemProvided)) {
                                                                            Size.Items item = Size.getItem(itemProvided);
                                                                            target.inventory.addItemStackToInventory(item.getItem());
                                                                        } else {
                                                                            ServerUtil.send(context.getSource(), "This is not a valid modifier item");
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                            } catch (NullPointerException e) {
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
