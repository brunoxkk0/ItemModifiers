package io.github.adainish.itemmodifiers.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.adainish.itemmodifiers.ItemModifiers;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ServerUtil {
    public static MinecraftServer server = ItemModifiers.server;

    private static final MinecraftServer SERVER = server;

    public static boolean isPlayerOnline(UUID uuid) {
        ServerPlayerEntity player = SERVER.getPlayerList().getPlayerByUUID(uuid);
        // IJ says it's always true ignore
        return player != null;
    }


    public static Optional <ServerPlayerEntity> getPlayerOptional(String name) {
        return Optional.ofNullable(server.getPlayerList().getPlayerByUsername(name));
    }


    public static ServerPlayerEntity getPlayer(String playerName) {
        return server.getPlayerList().getPlayerByUsername(playerName);
    }

    public static ServerPlayerEntity getPlayer(UUID uuid) {
        return server.getPlayerList().getPlayerByUUID(uuid);
    }

    public static void send(UUID uuid, String message) {
        getPlayer(uuid).sendMessage(new StringTextComponent(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), uuid);
    }

    public static void send(ServerPlayerEntity player, String message) {
        if (player == null)
            return;
        player.sendMessage(new StringTextComponent(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), player.getUniqueID());
    }

    public static void send(CommandSource sender, String message) {
        sender.sendFeedback(new StringTextComponent(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), false);
    }

    public static String formattedString(String s) {
        return s.replaceAll("&", "§");
    }

    public static List <String> formattedArrayList(List<String> list) {

        List<String> formattedList = new ArrayList <>();
        for (String s:list) {
            formattedList.add(formattedString(s));
        }

        return formattedList;
    }

    public static void runCommand(String cmd) {
        try {
            SERVER.getCommandManager().getDispatcher().execute(cmd, server.getCommandSource());
        } catch (CommandSyntaxException e) {
            ItemModifiers.log.error(e);
        }
    }
}
