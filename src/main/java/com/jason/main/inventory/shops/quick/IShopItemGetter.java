package com.jason.main.inventory.shops.quick;

@FunctionalInterface
public interface IShopItemGetter {
    ShopItem get(Shopper shopper);
}
