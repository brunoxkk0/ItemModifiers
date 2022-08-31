package io.github.adainish.itemmodifiers.wrapper;

import io.github.adainish.itemmodifiers.ItemModifiers;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.Level;

public class PermissionWrapper {
    public static String adminPermission = "itemmodifiers.admin";
    public PermissionWrapper() {
        registerPermissions();
    }
    public void registerPermissions() {
        registerCommandPermission(adminPermission, "The lucky block admin permission");
    }
    public static void registerCommandPermission(String s) {
        if (s == null || s.isEmpty()) {
            ItemModifiers.log.log(Level.FATAL, "Trying to register a permission node failed, please check any configs for null/empty Configs");
            return;
        }
        PermissionAPI.registerNode(s, DefaultPermissionLevel.NONE, s);
    }
    public static void registerCommandPermission(String s, String description) {
        if (s == null || s.isEmpty()) {
            ItemModifiers.log.log(Level.FATAL, "Trying to register a permission node failed, please check any configs for null/empty Configs");
            return;
        }
        PermissionAPI.registerNode(s, DefaultPermissionLevel.NONE, description);
    }
}
