package com.jason.main.inventory.shops.quick;

import com.jason.main.Util;
import com.jason.main.items.BedbugItem;
import com.jason.main.items.GolemItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ShopItem {
    public static final ShopItem
            STONE_SWORD = new ShopItem(new ItemStack(Material.STONE_SWORD), Material.IRON_INGOT, 10),
            IRON_SWORD = new ShopItem(new ItemStack(Material.IRON_SWORD), Material.GOLD_INGOT, 7),
            DIAMOND_SWORD = new ShopItem(new ItemStack(Material.DIAMOND_SWORD), Material.EMERALD, 3),
            KB_STICK = new ShopItem(() -> {
                ItemStack item = Util.namedItemStack(new ItemStack(Material.STICK), "Knockback Stick");
                item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
                return item;
            }, Material.GOLD_INGOT, 5),

            BOW_1 = new ShopItem(new ItemStack(Material.BOW), Material.GOLD_INGOT, 12),
            BOW_2 = new ShopItem(() -> {
                ItemStack bow = new ItemStack(Material.BOW);
                bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
                return bow;
            }, Material.GOLD_INGOT, 20),
            BOW_3 = new ShopItem(() -> {
                ItemStack bow = new ItemStack(Material.BOW);
                bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
                bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE,1);
                return bow;
            }, Material.EMERALD, 6),
            ARROW = new ShopItem(new ItemStack(Material.ARROW, 8), Material.GOLD_INGOT, 2),

            IRON_ARMOR = new ShopItem(new ItemStack(Material.IRON_BOOTS), Material.GOLD_INGOT, 12),
            CHAINMAIL_ARMOR = new ShopItem(new ItemStack(Material.CHAINMAIL_BOOTS), Material.IRON_INGOT, 24),
            DIAMOND_ARMOR = new ShopItem(new ItemStack(Material.DIAMOND_BOOTS), Material.EMERALD, 6),

            SHEARS = new ShopItem(new ItemStack(Material.SHEARS), Material.IRON_INGOT, 20),
            WOOD_AXE = new ShopItem(new ItemStack(Material.WOOD_AXE), Material.IRON_INGOT, 10),
            STONE_AXE = new ShopItem(new ItemStack(Material.STONE_AXE), Material.IRON_INGOT, 10),
            IRON_AXE = new ShopItem(new ItemStack(Material.IRON_AXE), Material.GOLD_INGOT, 3),
            DIAMOND_AXE = new ShopItem(new ItemStack(Material.DIAMOND_AXE), Material.IRON_INGOT, 6),
            WOOD_PICKAXE = new ShopItem(new ItemStack(Material.WOOD_PICKAXE), Material.IRON_INGOT, 10),
            IRON_PICKAXE = new ShopItem(new ItemStack(Material.IRON_PICKAXE), Material.IRON_INGOT, 10),
            GOLD_PICKAXE = new ShopItem(new ItemStack(Material.GOLD_PICKAXE), Material.GOLD_INGOT, 3),
            DIAMOND_PICKAXE = new ShopItem(new ItemStack(Material.DIAMOND_PICKAXE), Material.GOLD_INGOT, 6),

            WOOL = new ShopItem(new ItemStack(Material.WOOL, 16), Material.IRON_INGOT, 4),
            WOOD = new ShopItem(new ItemStack(Material.WOOD, 16), Material.GOLD_INGOT, 4),
            ENDER_STONE = new ShopItem(new ItemStack(Material.ENDER_STONE, 12), Material.IRON_INGOT, 24),
            OBSIDIAN = new ShopItem(new ItemStack(Material.OBSIDIAN, 4), Material.EMERALD, 4),
            GLASS = new ShopItem(Util.namedItemStack(new ItemStack(Material.STAINED_GLASS, 4), "Blast-Proof Glass(NOT WORKING)"), Material.IRON_INGOT, 12),
            CLAY = new ShopItem(new ItemStack(Material.HARD_CLAY, 16), Material.IRON_INGOT, 12),
            LADDERS = new ShopItem(new ItemStack(Material.LADDER, 16), Material.IRON_INGOT, 4),

            BRIDGE_EGG = new ShopItem(Util.namedItemStack(new ItemStack(Material.EGG), "Bridge Egg(NOT WORKING)"), Material.EMERALD, 1),
            FIREBALL = new ShopItem(Util.namedItemStack(new ItemStack(Material.FIREBALL), "Fireball"), Material.IRON_INGOT, 40), // TODO BedwarsItem implementation
            TNT = new ShopItem(new ItemStack(Material.TNT), Material.GOLD_INGOT, 4),//ssihodigsoighsoighosihgoisdhgoudsihgoidsuhgoiusdhgoiusdhgiosdhgiosdhgoishdgoihsdgiohsdioghdsiuoghsdiopghdsiogh
            SILVERFISH = new ShopItem(new BedbugItem().getItemStack(), Material.IRON_INGOT, 24),
            ENDER_PEARL = new ShopItem(new ItemStack(Material.ENDER_PEARL), Material.EMERALD, 4),
            GOLDEN_APPLE = new ShopItem(new ItemStack(Material.GOLDEN_APPLE), Material.GOLD_INGOT, 3),
            WATER_BUCKET = new ShopItem(new ItemStack(Material.WATER_BUCKET), Material.GOLD_INGOT, 3),
            IRON_GOLEM = new ShopItem(new GolemItem().getItemStack(), Material.IRON_INGOT, 120),
            MAGIC_MILK = new ShopItem(Util.namedItemStack(new ItemStack(Material.MILK_BUCKET), "Magic Milk(NOT WORKING)"), Material.GOLD_INGOT, 4),
            SPONGE = new ShopItem(new ItemStack(Material.NETHER_STAR, 4), Material.IRON_INGOT, 1),//ssihodigsoighsoighosihgoisdhgoudsihgoidsuhgoiusdhgoiusdhgiosdhgiosdhgoishdgoihsdgiohsdioghdsiuoghsdiopghdsiogh
            POPUP_TOWER = new ShopItem(Util.namedItemStack(new ItemStack(Material.CHEST), "Popup Tower(NOT WORKING)"), Material.IRON_INGOT, 24),

            INVIS_POTION = new ShopItem(() -> {
                ItemStack potion = Util.namedItemStack(new ItemStack(Material.POTION), "Invisibility Potion (30s)(NOT WORKING, DO NOT BUY)");
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30 * 20, 1), true);
                potion.setItemMeta(meta);
                return potion;
            }, Material.EMERALD, 2),//ssihodigsoighsoighosihgoisdhgoudsihgoidsuhgoiusdhgoiusdhgiosdhgiosdhgoishdgoihsdgiohsdioghdsiuoghsdiopghdsiogh
            JUMP_POTION = new ShopItem(() -> {
                ItemStack potion = Util.namedItemStack(new ItemStack(Material.POTION), "Jump Boost Potion (45s)");
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 45 * 20, 5), true);
                potion.setItemMeta(meta);
                return potion;
            }, Material.EMERALD, 1),
            SPEED_POTION = new ShopItem(() -> {
                ItemStack potion = Util.namedItemStack(new ItemStack(Material.POTION), "Speed Potion (45s)");
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 45 * 20, 2), true);
                potion.setItemMeta(meta);
                return potion;
            }, Material.EMERALD, 1);

    private final ItemStack itemStack;
    private final Material resourceType;
    private final int cost;

    private ShopItem(ItemStack itemStack, Material type, int cost) {
        this.itemStack = itemStack;
        this.resourceType = type;
        this.cost = cost;
    }

    private ShopItem(Supplier<ItemStack> itemStackSupplier, Material type, int cost) {
        this.itemStack = itemStackSupplier.get();
        this.resourceType = type;
        this.cost = cost;
    }

    public boolean onBought(Player player) {
        PurchaseHandler handler = new PurchaseHandler(player);
        Shopper shopper = new Shopper(player);

        List<Material> axeTiers = Arrays.stream(Shopper.AXES)
                .map(shopItem -> shopItem.getDisplayItemStack().getType())
                .collect(Collectors.toList());

        List<Material> pickaxeTiers = Arrays.stream(Shopper.PICKAXES)
                .map(shopItem -> shopItem.getDisplayItemStack().getType())
                .collect(Collectors.toList());

        List<Material> armorTiers = Arrays.stream(Shopper.ARMORS)
                .map(shopItem -> shopItem.getDisplayItemStack().getType())
                .collect(Collectors.toList());

        boolean success = tryBuyTiered(handler, axeTiers, shopper.axeTier)
                || tryBuyTiered(handler, pickaxeTiers, shopper.pickaxeTier)
                || tryBuyTiered(handler, armorTiers, shopper.armorTier)
                || handler.takeResource(new ItemStack(resourceType, cost));

        if (success) {
            PlayerInventory inv = player.getInventory();

            if (itemStack.getType().equals(Material.IRON_BOOTS)) {
                inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                inv.setBoots(new ItemStack(Material.IRON_BOOTS));
            }
            else if (itemStack.getType().equals(Material.CHAINMAIL_BOOTS)) {
                inv.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                inv.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
            }
            else if (itemStack.getType().equals(Material.DIAMOND_BOOTS)) {
                inv.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            }
            else handler.giveItems(getCleanItemStack());
        }

        return success;
    }

    public ItemStack getDisplayItemStack() {
        ItemStack displayItemStack = itemStack.clone();
        ItemMeta meta = displayItemStack.getItemMeta();
        meta.setLore(Collections.singletonList(ChatColor.GOLD.toString() + cost + " " + prettify(resourceType)));
        displayItemStack.setItemMeta(meta);
        return displayItemStack;
    }

    public ItemStack getCleanItemStack() {
        return itemStack;
    }

    private boolean tryBuyTiered(PurchaseHandler handler, List<Material> materialTiers, int currentTier) {
        if (materialTiers.contains(itemStack.getType()) && currentTier != 0) {
            if (itemStack.getType().equals(materialTiers.get(currentTier - 1))) return false; // max tier already
            return handler.takeResource(new ItemStack(resourceType, cost), new ItemStack(materialTiers.get(currentTier - 1)));
        } else return false;
    }

    private static String prettify(Material m) {
        String s = m.toString();
        String firstWord = s.split("_")[0];
        return firstWord.substring(0, 1).toUpperCase() + firstWord.substring(1).toLowerCase();
    }
}
