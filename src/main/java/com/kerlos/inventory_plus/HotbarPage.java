package com.kerlos.inventory_plus;

import net.minecraft.item.ItemStack;

public class HotbarPage {
    private final ItemStack[] slots = new ItemStack[9];

    public HotbarPage() {
        for (int i = 0; i < 9; i++) {
            slots[i] = ItemStack.EMPTY;
        }
    }

    public void saveFromInventory(net.minecraft.entity.player.PlayerInventory inv) {
        for (int i = 0; i < 9; i++) {
            slots[i] = inv.getStack(i).copy();
        }
    }

    public void loadToInventory(net.minecraft.entity.player.PlayerInventory inv) {
        for (int i = 0; i < 9; i++) {
            inv.setStack(i, slots[i].copy());
        }
    }

    public ItemStack getStack(int index) {
        return slots[index];
    }

    public void setStack(int index, ItemStack stack) {
        slots[index] = stack.copy();
    }

    public ItemStack[] getAll() {
        ItemStack[] copy = new ItemStack[9];
        for (int i = 0; i < 9; i++) copy[i] = slots[i].copy();
        return copy;
    }
}