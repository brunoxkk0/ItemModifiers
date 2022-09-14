package io.github.adainish.itemmodifiers.obj;

import info.pixelmon.repack.org.spongepowered.CommentedConfigurationNode;
import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.itemmodifiers.ItemModifiers;
import io.github.adainish.itemmodifiers.config.NatureConfig;
import io.github.adainish.itemmodifiers.util.ItemBuilder;
import io.github.adainish.itemmodifiers.util.Util;
import io.github.adainish.itemmodifiers.wrapper.PermissionWrapper;
import io.leangen.geantyref.TypeToken;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.stream.Collectors;

public class Nature {
    public static List <Items> items = new ArrayList <>();

    public static void loadItems() {
        items.clear();
        CommentedConfigurationNode rootNode = NatureConfig.getConfig().get().node();
        Map nodeMap = rootNode.childrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject == null) {
                ItemModifiers.log.info(nodeObject + " NULL");
            } else {
                String node = nodeObject.toString();
                if (node == null) {
                    ItemModifiers.log.info(node + " NODE NULL");
                } else {
                    ItemModifiers.log.info(node + "NEW Item ADDED");
                    items.add(new Items(node));
                }
            }
        }

    }

    public static List<String> itemNames() {
        return items.stream().map(item -> item.getKey()).collect(Collectors.toList());
    }

    public static boolean isItem(String name) {
        Iterator var1 = items.iterator();

        Items lvl;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            lvl = (Items) var1.next();
        } while (!lvl.getKey().equalsIgnoreCase(name));

        return true;
    }

    public static Items getItem(String key) {
        Iterator var1 = items.iterator();

        Items lvl;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            lvl = (Items) var1.next();
        } while (!lvl.getKey().equalsIgnoreCase(key));

        return lvl;
    }

    public static class Items {
        private String key;
        private List<String> enumNatures = Arrays.asList("Adamant", "Bashful", "Bold", "Brave", "Calm", "Careful", "Docile", "Gentle", "Hardy", "Hasty", "Impish", "Jolly", "Lax", "Lonely", "Mild", "Modest", "Naive", "Naughty", "Quiet", "Quirky", "Rash", "Relaxed", "Sassy", "Serious", "Timid");
        private boolean allowDitto = false;
        private boolean allowLegends = false;
        private boolean allowUltraBeasts = false;
        private boolean enchantItem = false;
        private String display;
        private String itemType = "minecraft:paper";
        private String permission = "";
        private List<String> specFlags = new ArrayList <>();
        private List<String> Lore;

        public Items(String key) {
            this.setKey(key);
            try {
                this.setEnumNatures(NatureConfig.getConfig().get().node(key, "Natures").getList(TypeToken.get(String.class)));
            } catch (SerializationException e) {
                e.printStackTrace();
            }
            this.setEnchantItem(NatureConfig.getConfig().get().node(key, "Enchanted").getBoolean());
            this.setAllowDitto(NatureConfig.getConfig().get().node(key, "AllowDitto").getBoolean());
            this.setAllowLegends(NatureConfig.getConfig().get().node(key, "AllowLegends").getBoolean());
            this.setAllowUltraBeasts(NatureConfig.getConfig().get().node(key, "AllowUBs").getBoolean());
            this.setItemType(NatureConfig.getConfig().get().node(key, "ItemString").getString());
            this.setPermission(NatureConfig.getConfig().get().node(key, "Permission").getString());
            this.setDisplay(NatureConfig.getConfig().get().node(key, "Display").getString());
            try {
                this.setSpecFlags(NatureConfig.getConfig().get().node(key, "Specs").getList(TypeToken.get(String.class)));
            } catch (SerializationException e) {
                e.printStackTrace();
            }
            try {
                this.setLore(NatureConfig.getConfig().get().node(key, "Lore").getList(TypeToken.get(String.class)));
            } catch (SerializationException e) {
                e.printStackTrace();
            }
            PermissionWrapper.registerCommandPermission(permission);
        }

        public String getFormattedName() {
            return this.getDisplay().replaceAll("&", "§");
        }

        public List<String> getFormattedText() {
            List<String> formattedInfo = new ArrayList<>();
            for (String s: this.getLore()) {
                formattedInfo.add(s.replaceAll("&", "§"));
            }
            return formattedInfo;
        }
        public ItemStack getItem() {
            ItemBuilder itemBuilder = null;
            try {
                itemBuilder = new ItemBuilder(this.itemType);
            } catch (Exception e) {
                ItemModifiers.log.log(Level.WARN, e);
                return null;
            }
            itemBuilder.setName(Util.formattedString(display));
            itemBuilder.setLore(Util.formattedArrayList(getLore()));
            if (enchantItem)
                itemBuilder.setEnchanted();
            ItemStack stack = itemBuilder.build();
            stack.getTag().putBoolean("itemmodifier", true);
            stack.getTag().putString("NatureModifier", this.key);
            stack.getTag().putString("itemType", "Nature");
            return stack;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List <String> getEnumNatures() {
            return enumNatures;
        }

        public void setEnumNatures(List <String> enumNatures) {
            this.enumNatures = enumNatures;
        }

        public boolean isAllowDitto() {
            return allowDitto;
        }

        public void setAllowDitto(boolean allowDitto) {
            this.allowDitto = allowDitto;
        }

        public boolean isAllowLegends() {
            return allowLegends;
        }

        public void setAllowLegends(boolean allowLegends) {
            this.allowLegends = allowLegends;
        }

        public boolean isAllowUltraBeasts() {
            return allowUltraBeasts;
        }

        public void setAllowUltraBeasts(boolean allowUltraBeasts) {
            this.allowUltraBeasts = allowUltraBeasts;
        }

        public boolean isEnchantItem() {
            return enchantItem;
        }

        public void setEnchantItem(boolean enchantItem) {
            this.enchantItem = enchantItem;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public List <String> getSpecFlags() {
            return specFlags;
        }

        public void setSpecFlags(List <String> specFlags) {
            this.specFlags = specFlags;
        }

        public List <String> getLore() {
            return Lore;
        }

        public void setLore(List <String> lore) {
            Lore = lore;
        }
    }
}
