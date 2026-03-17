package com.kerlos.inventory_plus;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryPlus implements ClientModInitializer {
    public static final String MOD_ID = "inventory-plus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    static MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Inventory Plus initialized successfully");

        // Attach our rendering code to before the chat hud layer. Our layer will render right before the chat. The API will take care of z spacing.
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(InventoryPlus.MOD_ID, "before_chat"), InventoryPlus::render);
    }

    private static void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        int color = 0xFFFF0000; // Red

        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        int x = 50;
        int y = 20;

        drawContext.fill(width / 2 - x, height / 2 - y, width / 2 - (x / 2), height / 2  -y / 2, color);
    }
}