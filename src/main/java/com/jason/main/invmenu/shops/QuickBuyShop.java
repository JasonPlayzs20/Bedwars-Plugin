package com.jason.main.invmenu.shops;

import com.jason.main.invmenu.InventoryMenu;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class QuickBuyShop extends InventoryMenu {
    private static final IShopItemGetter[][] PAGES = {
            { shopper -> ShopItem.BRIDGE_EGG,
                    shopper -> shopper.getToolUpgrade(Shopper.PICKAXES, shopper.pickaxeTier),
                    shopper -> ShopItem.FIREBALL,
                    shopper -> ShopItem.IRON_ARMOR,
                    shopper -> ShopItem.TNT,
                    shopper -> ShopItem.INVIS_POTION,
                    shopper -> ShopItem.SILVERFISH,
                    shopper -> ShopItem.ENDER_PEARL,
                    shopper -> shopper.getToolUpgrade(Shopper.AXES, shopper.axeTier),
                    shopper -> ShopItem.STONE_SWORD,
                    shopper -> ShopItem.WOOL,
                    shopper -> ShopItem.LADDERS,
                    shopper -> ShopItem.JUMP_POTION,
                    shopper -> ShopItem.ENDER_STONE,
                    shopper -> ShopItem.GLASS,
                    shopper -> ShopItem.SHEARS,
                    shopper -> ShopItem.IRON_SWORD,
                    shopper -> ShopItem.GOLDEN_APPLE,
                    shopper -> ShopItem.KB_STICK,
                    shopper -> ShopItem.WATER_BUCKET,
                    shopper -> ShopItem.WOOD
            },
            { shopper -> ShopItem.WOOL,
                    shopper -> ShopItem.CLAY,
                    shopper -> ShopItem.GLASS,
                    shopper -> ShopItem.ENDER_STONE,
                    shopper -> ShopItem.LADDERS,
                    shopper -> ShopItem.WOOD,
                    shopper -> ShopItem.OBSIDIAN
            },

            { shopper -> ShopItem.STONE_SWORD,
                    shopper -> ShopItem.IRON_SWORD,
                    shopper -> ShopItem.DIAMOND_SWORD,
                    shopper -> ShopItem.KB_STICK
            },

            { shopper -> ShopItem.CHAINMAIL_ARMOR,
                    shopper -> ShopItem.IRON_ARMOR,
                    shopper -> ShopItem.DIAMOND_ARMOR
            },

            { shopper -> ShopItem.SHEARS,
                    shopper -> shopper.getToolUpgrade(Shopper.PICKAXES, shopper.pickaxeTier),
                    shopper -> shopper.getToolUpgrade(Shopper.AXES, shopper.axeTier)
            }
    };

    private int page = 0;

    public QuickBuyShop(Player player) {
        super(player, 6, "Quick Buy");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void populateMenu() {
        inventory.clear();

        List<Material> topBar = Arrays.asList(
            Material.NETHER_STAR,
            Material.HARD_CLAY,
            Material.GOLD_SWORD,
            Material.CHAINMAIL_BOOTS,
            Material.STONE_PICKAXE,
            Material.BOW,
            Material.BREWING_STAND_ITEM,
            Material.TNT
        );
        for (int i = 0; i < 8; i++) {
            int fi = i;
            setButton(0, i, new ItemStack(topBar.get(i)), (menu, itemStack, button) -> page = fi);
        }

        for (int i = 0; i < 9; i++) {
            setItem(1, i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (i == page ? DyeColor.LIME : DyeColor.GRAY).getData()));
        }

        Shopper shopper = new Shopper(player);
        IShopItemGetter[] shopItems = PAGES[page];
        int i = 0;
        for (int s : new int[] {19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43}) {
            if (i >= shopItems.length) break;
            ShopItem shopItem = shopItems[i].get(shopper);
            setButton(s / 9, s % 9, shopItem.getItemStack(), (menu, itemStack, button) -> {
                if (!shopItem.onBought(player)) player.sendMessage(ChatColor.RED + "You broke as hell boy");
            });
            i++;
        }
    }
}
