package com.kerlos.inventory_plus.utils;

import com.kerlos.inventory_plus.InventoryPlusClient;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import java.util.Objects;

public class InventoryPlusCommands {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("inventory-plus")
                                .requires(source -> Objects.requireNonNull(source.getPlayer()).getAbilities().creativeMode)
                // --- INDEX ---
                .then(ClientCommandManager.literal("index")

                        .then(ClientCommandManager.literal("set").then(ClientCommandManager.argument("value", IntegerArgumentType.integer(0, 7)).executes(ctx -> {
                            int value = IntegerArgumentType.getInteger(ctx, "value");

                            InventoryPlusClient.HOTBAR_MANAGER.setSelectedPage(value);

                            ctx.getSource().sendFeedback(Text.literal("Set current index to" + InventoryPlusClient.HOTBAR_MANAGER.getCurrentPage()));
                            return 1;
                        })))

                        .then(ClientCommandManager.literal("get").executes(ctx -> {
                            int value = InventoryPlusClient.HOTBAR_MANAGER.getSelectedPage();

                            ctx.getSource().sendFeedback(Text.literal("Current index: " + value));
                            return 1;
                        })))

                // --- SAVE ---
                .then(ClientCommandManager.literal("save").executes(ctx -> {
                    InventoryPlusClient.HOTBAR_MANAGER.saveCurrentPage();
                    ctx.getSource().sendFeedback(Text.literal("Hotbar saved"));
                    return 1;
                }))

                // --- LOAD ---
                .then(ClientCommandManager.literal("load").executes(ctx -> {
                    InventoryPlusClient.HOTBAR_MANAGER.loadPage(InventoryPlusClient.HOTBAR_MANAGER.getSelectedPage());
                    ctx.getSource().sendFeedback(Text.literal("Hotbar loaded from index " + InventoryPlusClient.HOTBAR_MANAGER.getSelectedPage()));
                    return 1;
                }))

                .then(ClientCommandManager.literal("help").executes(ctx -> {

                    ctx.getSource().sendFeedback(Text.literal("§6============== Inventory Plus =============="));
                    ctx.getSource().sendFeedback(Text.literal("§e/inventory-plus save §7→ Save current hotbar"));
                    ctx.getSource().sendFeedback(Text.literal("§e/inventory-plus load §7→ Load selected hotbar"));
                    ctx.getSource().sendFeedback(Text.literal("§e/inventory-plus index set <0-7> §7→ Select page"));
                    ctx.getSource().sendFeedback(Text.literal("§e/inventory-plus index get §7→ Show current page"));

                    return 1;
                }))));
    }
}