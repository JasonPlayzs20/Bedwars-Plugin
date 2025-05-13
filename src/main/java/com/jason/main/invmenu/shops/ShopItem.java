package com.jason.main.invmenu.shops;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ShopItem {
    public static final ShopItem
            STONE_SWORD = new ShopItem(new ItemStack(Material.STONE_SWORD), Material.IRON_INGOT, 1),
            IRON_SWORD = new ShopItem(new ItemStack(Material.IRON_SWORD), Material.IRON_INGOT, 1),
            DIAMOND_SWORD = new ShopItem(new ItemStack(Material.DIAMOND_SWORD), Material.IRON_INGOT, 1),
            KB_STICK = new ShopItem(() -> {
                ItemStack item = new ItemStack(Material.STICK);
                item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
                return item;
            }, Material.IRON_INGOT, 1),

            IRON_ARMOR = new ShopItem(new ItemStack(Material.IRON_BOOTS), Material.IRON_INGOT, 1),
            CHAINMAIL_ARMOR = new ShopItem(new ItemStack(Material.CHAINMAIL_BOOTS), Material.IRON_INGOT, 1),
            DIAMOND_ARMOR = new ShopItem(new ItemStack(Material.DIAMOND_BOOTS), Material.IRON_INGOT, 1),

            SHEARS = new ShopItem(new ItemStack(Material.SHEARS), Material.IRON_INGOT, 1),
            WOOD_AXE = new ShopItem(new ItemStack(Material.WOOD_AXE), Material.IRON_INGOT, 1),
            STONE_AXE = new ShopItem(new ItemStack(Material.STONE_AXE), Material.IRON_INGOT, 1),
            IRON_AXE = new ShopItem(new ItemStack(Material.IRON_AXE), Material.IRON_INGOT, 1),
            DIAMOND_AXE = new ShopItem(new ItemStack(Material.DIAMOND_AXE), Material.IRON_INGOT, 1),
            WOOD_PICKAXE = new ShopItem(new ItemStack(Material.WOOD_PICKAXE), Material.IRON_INGOT, 1),
            IRON_PICKAXE = new ShopItem(new ItemStack(Material.IRON_PICKAXE), Material.IRON_INGOT, 1),
            GOLD_PICKAXE = new ShopItem(new ItemStack(Material.GOLD_PICKAXE), Material.IRON_INGOT, 1),
            DIAMOND_PICKAXE = new ShopItem(new ItemStack(Material.DIAMOND_PICKAXE), Material.IRON_INGOT, 1),

            WOOL = new ShopItem(() -> new ItemStack(Material.WOOL, 16), Material.IRON_INGOT, 1),
            WOOD = new ShopItem(() -> new ItemStack(Material.WOOD, 16), Material.IRON_INGOT, 1),
            ENDER_STONE = new ShopItem(() -> new ItemStack(Material.ENDER_STONE, 12), Material.IRON_INGOT, 1),
            OBSIDIAN = new ShopItem(() -> new ItemStack(Material.OBSIDIAN, 4), Material.IRON_INGOT, 1),
            GLASS = new ShopItem(() -> new ItemStack(Material.STAINED_GLASS, 4), Material.IRON_INGOT, 1),
            CLAY = new ShopItem(() -> new ItemStack(Material.HARD_CLAY, 16), Material.IRON_INGOT, 1),
            LADDERS = new ShopItem(new ItemStack(Material.LADDER, 16), Material.IRON_INGOT, 1),

            BRIDGE_EGG = new ShopItem(() -> new ItemStack(Material.EGG), Material.IRON_INGOT, 1),
            FIREBALL = new ShopItem(() -> new ItemStack(Material.FIREBALL), Material.IRON_INGOT, 1),
            TNT = new ShopItem(() -> new ItemStack(Material.TNT), Material.IRON_INGOT, 1),
            SILVERFISH = new ShopItem(() -> new ItemStack(Material.SNOW_BALL), Material.IRON_INGOT, 1),
            ENDER_PEARL = new ShopItem(() -> new ItemStack(Material.ENDER_PEARL), Material.IRON_INGOT, 1),
            GOLDEN_APPLE = new ShopItem(() -> new ItemStack(Material.GOLDEN_APPLE), Material.IRON_INGOT, 1),
            WATER_BUCKET = new ShopItem(() -> new ItemStack(Material.WATER_BUCKET), Material.IRON_INGOT, 1),

            INVIS_POTION = new ShopItem(() -> {
                ItemStack potion = new ItemStack(Material.POTION);
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30 * 20, 1), true);
                potion.setItemMeta(meta);
                return potion;
            }, Material.IRON_INGOT, 1),
            JUMP_POTION = new ShopItem(() -> {
                ItemStack potion = new ItemStack(Material.POTION);
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 45 * 20, 5), true);
                potion.setItemMeta(meta);
                return potion;
            }, Material.IRON_INGOT, 1);

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
                .map(shopItem -> shopItem.getItemStack().getType())
                .collect(Collectors.toList());

        List<Material> pickaxeTiers = Arrays.stream(Shopper.PICKAXES)
                .map(shopItem -> shopItem.getItemStack().getType())
                .collect(Collectors.toList());

        List<Material> armorTiers = Arrays.stream(Shopper.ARMORS)
                .map(shopItem -> shopItem.getItemStack().getType())
                .collect(Collectors.toList());

        boolean success = tryBuyTiered(handler, axeTiers, shopper.axeTier)
                || tryBuyTiered(handler, pickaxeTiers, shopper.pickaxeTier)
                || tryBuyTiered(handler, armorTiers, shopper.armorTier)
                || handler.takeResource(new ItemStack(resourceType, cost));
//        if (AXE_TIERS.contains(itemStack.getType()) && shopper.axeTier != 0) {
//            if (itemStack.getType().equals(AXE_TIERS.get(shopper.axeTier - 1))) return false;
//            success = handler.takeResource(new ItemStack(resourceType, cost), new ItemStack(AXE_TIERS.get(shopper.axeTier - 1)));
//        }
//        else if (PICKAXE_TIERS.contains(itemStack.getType()) && shopper.pickaxeTier != 0) {
//            if (itemStack.getType().equals(PICKAXE_TIERS.get(shopper.pickaxeTier - 1))) return false;
//            success = handler.takeResource(new ItemStack(resourceType, cost), new ItemStack(PICKAXE_TIERS.get(shopper.pickaxeTier - 1)));
//        }
//        else if (ARMOR_TIERS.contains(itemStack.getType()) && shopper.armorTier != 0) {
//            if (itemStack.getType().equals(ARMOR_TIERS.get(shopper.armorTier - 1))) return false;
//            success = handler.takeResource(new ItemStack(resourceType, cost), new ItemStack(ARMOR_TIERS.get(shopper.armorTier - 1)));
//        }
//        else success = handler.takeResource(new ItemStack(resourceType, cost));

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
            else handler.giveItems(itemStack);
        }

        return success;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private boolean tryBuyTiered(PurchaseHandler handler, List<Material> materialTiers, int currentTier) {
        if (materialTiers.contains(itemStack.getType()) && currentTier != 0) {
            if (itemStack.getType().equals(materialTiers.get(currentTier - 1))) return false; // max tier already
            return handler.takeResource(new ItemStack(resourceType, cost), new ItemStack(materialTiers.get(currentTier - 1)));
        } else return false;
    }
}
