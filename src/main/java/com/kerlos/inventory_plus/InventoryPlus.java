package com.kerlos.inventory_plus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryPlus implements ClientModInitializer {

    public static KeyBinding toggleHudKey;

    public static final String MOD_ID = "inventory-plus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    static MinecraftClient mc = MinecraftClient.getInstance();

    private static final KeyBinding.Category CATEGORY = KeyBinding.Category.create(Identifier.of("inventory-plus", "title"));

    public static final MultiHotbarManager HOTBAR_MANAGER = new MultiHotbarManager(8);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Inventory Plus initialized successfully");

        // Zoom
        toggleHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.inventory-plus.toggleHud",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleHudKey.wasPressed()) {
                mc.gameRenderer.getClient().setScreen(new WheelScreen(Text.of("Title")));
            }
        });
    }
}