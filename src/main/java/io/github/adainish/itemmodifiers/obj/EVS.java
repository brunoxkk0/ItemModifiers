package io.github.adainish.itemmodifiers.obj;

import info.pixelmon.repack.org.spongepowered.CommentedConfigurationNode;
import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.github.adainish.itemmodifiers.ItemModifiers;
import io.github.adainish.itemmodifiers.config.EVSConfig;
import io.github.adainish.itemmodifiers.util.ItemBuilder;
import io.github.adainish.itemmodifiers.util.Util;
import io.github.adainish.itemmodifiers.wrapper.PermissionWrapper;
import io.leangen.geantyref.TypeToken;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.stream.Collectors;

public class EVS {
    public static List <Items> items = new ArrayList <>();

    public static void loadItems() {
        items.clear();
        CommentedConfigurationNode rootNode = EVSConfig.getConfig().get().node();
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
        private boolean enchantItem = false;
        private List<String> statsTypes = Arrays.asList("HP",
                "Attack",
                "Defence",
                "SpecialAttack",
                "SpecialDefence",
                "Speed");
        private boolean increase = false;
        private boolean allowDitto = false;
        private boolean allowLegends = false;
        private boolean allowUltraBeasts = false;
        private String display;
        private String itemType = "minecraft:paper";
        private String permission = "";
        private List<String> Lore;
        private List<String> specFlags = new ArrayList <>();
        private int amount = 1;

        public Items(String key) {
            this.setKey(key);
            this.setIncrease(EVSConfig.getConfig().get().node(key, "Increase").getBoolean());
            try {
                this.setStatsTypes(EVSConfig.getConfig().get().node(key, "Stats").getList(TypeToken.get(String.class)));
            } catch (SerializationException e) {
                e.printStackTrace();
            }
            this.setEnchantItem(EVSConfig.getConfig().get().node(key, "Enchanted").getBoolean());
            this.setItemType(EVSConfig.getConfig().get().node(key, "ItemString").getString());
            this.setPermission(EVSConfig.getConfig().get().node(key, "Permission").getString());
            this.setDisplay(EVSConfig.getConfig().get().node(key, "Display").getString());
            this.setAllowDitto(EVSConfig.getConfig().get().node(key, "AllowDitto").getBoolean());
            this.setAllowLegends(EVSConfig.getConfig().get().node(key, "AllowLegends").getBoolean());
            this.setAllowUltraBeasts(EVSConfig.getConfig().get().node(key, "AllowUBs").getBoolean());
            try {
                this.setSpecFlags(EVSConfig.getConfig().get().node(key, "Specs").getList(TypeToken.get(String.class)));
            } catch (SerializationException e) {
                e.printStackTrace();
            }
            try {
                this.setLore(EVSConfig.getConfig().get().node(key, "Lore").getList(TypeToken.get(String.class)));
            } catch (SerializationException e) {
                e.printStackTrace();
            }
            this.setAmount(EVSConfig.getConfig().get().node(key, "Amount").getInt());
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
            ItemStack stack = itemBuilder.build();
            stack.getTag().putBoolean("itemmodifier", true);
            stack.getTag().putString("EVSModifier", this.key);
            stack.getTag().putString("itemType", "EVS");
            return stack;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isEnchantItem() {
            return enchantItem;
        }

        public void setEnchantItem(boolean enchantItem) {
            this.enchantItem = enchantItem;
        }

        public List <String> getStatsTypes() {
            return statsTypes;
        }

        public void setStatsTypes(List <String> statsTypes) {
            this.statsTypes = statsTypes;
        }

        public boolean isIncrease() {
            return increase;
        }

        public void setIncrease(boolean increase) {
            this.increase = increase;
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

        public List <String> getLore() {
            return Lore;
        }

        public void setLore(List <String> lore) {
            Lore = lore;
        }

        public List <String> getSpecFlags() {
            return specFlags;
        }

        public void setSpecFlags(List <String> specFlags) {
            this.specFlags = specFlags;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
