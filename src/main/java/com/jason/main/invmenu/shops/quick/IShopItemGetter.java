package com.jason.main.invmenu.shops.quick;

@FunctionalInterface
public interface IShopItemGetter {
    ShopItem get(Shopper shopper);
}
