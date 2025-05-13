package com.jason.main.invmenu.shops;

@FunctionalInterface
public interface IShopItemGetter {
    ShopItem get(Shopper shopper);
}
