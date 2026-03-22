package com.kerlos.inventory_plus.utils;

import net.minecraft.entity.player.PlayerInventory;
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

    public void loadToInventory(PlayerInventory inv) {

        // Copier uniquement les index (pas les items)
        int[] targetSlots = new int[9];

        // Trouver où sont les items dans l'inventaire
        for (int i = 0; i < 9; i++) {
            targetSlots[i] = findMatchingSlot(inv, slots[i]);
        }

        // Faire les swaps
        for (int i = 0; i < 9; i++) {
            int from = targetSlots[i];

            if (from == -1) continue; // item pas trouvé → skip

            if (from == i) continue; // déjà au bon endroit

            ItemStack a = inv.getStack(i);
            ItemStack b = inv.getStack(from);

            inv.setStack(i, b);
            inv.setStack(from, a);

            // 🔥 IMPORTANT : update les autres indices
            for (int j = 0; j < 9; j++) {
                if (targetSlots[j] == i) targetSlots[j] = from;
                else if (targetSlots[j] == from) targetSlots[j] = i;
            }
        }
    }

    private int findMatchingSlot(PlayerInventory inv, ItemStack target) {
        for (int i = 0; i < inv.size(); i++) {
            if (ItemStack.areItemsAndComponentsEqual(inv.getStack(i), target)) {
                return i;
            }
        }
        return -1;
    }

    private int findFreeSlot(boolean[] used, int size) {
        for (int i = 9; i < size; i++) {
            if (!used[i]) return i;
        }
        return -1;
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