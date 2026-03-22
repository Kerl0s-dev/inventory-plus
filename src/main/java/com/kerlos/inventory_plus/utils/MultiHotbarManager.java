package com.kerlos.inventory_plus.utils;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class MultiHotbarManager {

    public final List<HotbarPage> pages = new ArrayList<>();
    private int currentPage = 0;    // page actuellement chargée dans l’inventaire
    private int selectedPage = 0;   // page choisie sur la roue

    public static MinecraftClient mc = MinecraftClient.getInstance();

    public MultiHotbarManager(int numPages) {
        for (int i = 0; i < numPages; i++) {
            pages.add(new HotbarPage());
        }
    }

    // --- getter / setter pour la page sélectionnée ---
    public void setSelectedPage(int index) {
        if (index >= 0 && index < pages.size()) {
            selectedPage = index;
        }
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public int getCurrentPageIndex() {
        return currentPage;
    }

    public HotbarPage getCurrentPage() {
        return pages.get(currentPage);
    }

    // --- sauvegarder la page sélectionnée dans l'inventaire ---
    public void saveCurrentPage() {
        if (mc.player == null) return;
        pages.get(selectedPage).saveFromInventory(mc.player.getInventory());
    }

    // --- charger la page sélectionnée dans l'inventaire ---
    public void loadPage(int index) {
        if (mc.player == null || index < 0 || index >= pages.size()) return;

        // charger la nouvelle page
        pages.get(index).loadToInventory(mc.player.getInventory());

        currentPage = index;
    }

    private boolean initialized = false;

    public void initFromPlayer() {
        if (initialized || mc.player == null) return;

        for (HotbarPage page : pages) {
            page.saveFromInventory(mc.player.getInventory());
        }

        initialized = true;
    }
}