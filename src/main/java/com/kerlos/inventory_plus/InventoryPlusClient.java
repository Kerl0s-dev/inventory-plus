package com.kerlos.inventory_plus;

import com.kerlos.inventory_plus.utils.InventoryPlusCommands;
import com.kerlos.inventory_plus.utils.InputRegistry;
import com.kerlos.inventory_plus.utils.MultiHotbarManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryPlusClient implements ClientModInitializer {

    public static final String MOD_ID = "Inventory Plus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static final MultiHotbarManager HOTBAR_MANAGER = new MultiHotbarManager(8);

    @Override
    public void onInitializeClient() {
        LOGGER.info(MOD_ID + " initialized successfully");

        InventoryPlusCommands.register();
        LOGGER.info("Commands registered successfully");

        InputRegistry.register();
        LOGGER.info("Inputs registered successfully");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (InputRegistry.toggleHudKey.wasPressed()) {
                mc.gameRenderer.getClient().setScreen(new WheelScreen(Text.of("Title")));
            }
        });
    }
}