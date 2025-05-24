package com.jason.main.inventory.shops.diamond;

import com.jason.main.Emums.DiamondUpgrade;
import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.inventory.InventoryMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static com.jason.main.Util.consumeItem;
import static com.jason.main.Util.namedItemStack;

public class DiamondShop extends InventoryMenu {
    public static final List<DiamondUpgrade> TRAPS = Arrays.asList(DiamondUpgrade.ITSATRAP, DiamondUpgrade.MININGTRAP, DiamondUpgrade.ALARMTRAP, DiamondUpgrade.COUNTERTRAP);

    private static final HashMap<DiamondUpgrade, UpgradeItem> upgradeItems = new HashMap<>();
    static {
        upgradeItems.put(DiamondUpgrade.SHARPNESS, new UpgradeItem(namedItemStack(new ItemStack(Material.DIAMOND_SWORD), "Sharpness"), new int[] { 4 }));
        upgradeItems.put(DiamondUpgrade.PROTECTION, new UpgradeItem(namedItemStack(new ItemStack(Material.DIAMOND_CHESTPLATE), "Protection"), new int[] { 2, 4, 8, 16 }));
        upgradeItems.put(DiamondUpgrade.FORGE, new UpgradeItem(namedItemStack(new ItemStack(Material.FURNACE), "Quick Forge"), new int[] { 2, 5, 6, 8 }));
        upgradeItems.put(DiamondUpgrade.HASTE, new UpgradeItem(namedItemStack(new ItemStack(Material.GOLD_PICKAXE), "Maniac Minor"), new int[] { 2, 4 }));

//        upgradeItems.put(DiamondUpgrade.ALARMTRAP, new UpgradeItem(namedItemStack(new ItemStack(Material.REDSTONE_TORCH_ON), "Alarm Trap"), new int[] { 1 }));
//        upgradeItems.put(DiamondUpgrade.COUNTERTRAP, new UpgradeItem(namedItemStack(new ItemStack(Material.FEATHER), "Counter Trap"), new int[] { 1 }));
//        upgradeItems.put(DiamondUpgrade.ITSATRAP, new UpgradeItem(namedItemStack(new ItemStack(Material.TRIPWIRE), "It's a Trap!"), new int[] { 1 }));
        upgradeItems.put(DiamondUpgrade.MININGTRAP, new UpgradeItem(namedItemStack(new ItemStack(Material.WOOD_PICKAXE), "Mining Fatigue Trap"), new int[] { 1 }));

        upgradeItems.put(DiamondUpgrade.HEALPOOL, new UpgradeItem(namedItemStack(new ItemStack(Material.BEACON), "Heal Pool"), new int[] { 1 }));
        upgradeItems.put(DiamondUpgrade.DRAGONBUFF, new UpgradeItem(namedItemStack(new ItemStack(Material.DRAGON_EGG), "Dragon Buff"), new int[] { 5 }));
    }

    private final BedwarsPlayer bwPlayer;
    private final GameManager bwInstance;

    public DiamondShop(Player player, GameManager bwInstance) {
        super(player, 5, "Team Upgrade");
        this.bwInstance = bwInstance;
        this.bwPlayer = bwInstance.bedwarsPlayers.get(player);
    }

    @Override
    protected void populateMenu() {
        super.populateMenu();

        HashMap<DiamondUpgrade, Integer> upgrades = bwPlayer.getTeam().getDiamondUpgrades();

        List<DiamondUpgrade> upgradeKeys = new ArrayList<>(upgradeItems.keySet());

        int row = 1;
        int col = 1;

        for (DiamondUpgrade upgrade : upgradeKeys) {
            int priceIdx = upgrades.get(upgrade);

            UpgradeItem upgradeItem = upgradeItems.get(upgrade);

            if (priceIdx >= upgradeItem.getPrices().length) { // maxed out already
                ItemStack is = upgradeItem.getItemStack();
                ItemMeta im = is.getItemMeta();
                im.setLore(Arrays.asList(
                        ChatColor.GOLD + "Tier " + priceIdx,
                        ChatColor.RED + "No more upgrades"
                ));
                is.setItemMeta(im);

                setItem(row, col, is);
            } else {
                if (TRAPS.contains(upgrade) && bwPlayer.getTeam().getTrap() != null) { // only allow one trap at a time
                    ItemStack is = upgradeItem.getItemStack();
                    ItemMeta im = is.getItemMeta();
                    im.setLore(Collections.singletonList(
                            ChatColor.RED + "Already bought a trap!"
                    ));
                    is.setItemMeta(im);

                    setItem(row, col, is);
                } else {
                    int price = upgradeItem.getPrices()[priceIdx];

                    ItemStack is = upgradeItem.getItemStack();
                    ItemMeta im = is.getItemMeta();
                    im.setLore(Arrays.asList(
                            ChatColor.GOLD + (priceIdx == 0 ? "Didn't buy yet" : "Tier " + priceIdx),
                            ChatColor.GOLD + "Click to upgrade: " + price + " diamonds"
                    ));
                    is.setItemMeta(im);

                    setButton(row, col, is, (menu, itemStack, button) -> {
                        if (consumeItem(player, price, Material.DIAMOND)) {
                            player.getWorld().getPlayers().forEach(player1 -> {
                                if (Arenas.getArena(player1.getWorld()).bedwarsPlayers.get(player1).team.teamColors == Arenas.getArena(player.getWorld()).bedwarsPlayers.get(player).team.teamColors) {
                                    player1.getActivePotionEffects().clear();
                                    Arenas.getArena(player1.getWorld()).bedwarsPlayers.get(player1).getTeam().getDiamondUpgrades().put(upgrade, priceIdx + 1);
                                    player1.sendMessage(ChatColor.GREEN + "Upgraded");
                                }
                            });

                        } else player.sendMessage(ChatColor.RED + "You broke as hell boy");
                    });
                }
            }

            if (++col == 8) {
                col = 1;
                row++;
            }
        }
    }
}
