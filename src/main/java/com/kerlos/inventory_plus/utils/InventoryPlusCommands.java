package com.kerlos.inventory_plus.utils;

import com.kerlos.inventory_plus.InventoryPlusClient;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import java.util.Objects;

public class InventoryPlusCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, registrationEnvironment) -> dispatcher.register(CommandManager.literal("inventory-plus")
                                .requires(source -> Objects.requireNonNull(source.getPlayer()).getAbilities().creativeMode)
                // --- INDEX ---
                .then(CommandManager.literal("index")

                        .then(CommandManager.literal("set").then(CommandManager.argument("value", IntegerArgumentType.integer(0, 7)).executes(ctx -> {
                            int value = IntegerArgumentType.getInteger(ctx, "value");

                            InventoryPlusClient.HOTBAR_MANAGER.setSelectedPage(value);

                            ctx.getSource().sendMessage(Text.literal("Set current index to" + InventoryPlusClient.HOTBAR_MANAGER.getCurrentPage()));
                            return 1;
                        })))

                        .then(CommandManager.literal("get").executes(ctx -> {
                            int value = InventoryPlusClient.HOTBAR_MANAGER.getSelectedPage();

                            ctx.getSource().sendMessage(Text.literal("Current index: " + value));
                            return 1;
                        })))

                // --- SAVE ---
                .then(CommandManager.literal("save").executes(ctx -> {
                    InventoryPlusClient.HOTBAR_MANAGER.saveCurrentPage();
                    ctx.getSource().sendMessage(Text.literal("Hotbar saved"));
                    return 1;
                }))

                // --- LOAD ---
                .then(CommandManager.literal("load").executes(ctx -> {
                    InventoryPlusClient.HOTBAR_MANAGER.loadPage(InventoryPlusClient.HOTBAR_MANAGER.getSelectedPage());
                    ctx.getSource().sendMessage(Text.literal("Hotbar loaded from index " + InventoryPlusClient.HOTBAR_MANAGER.getSelectedPage()));
                    return 1;
                }))

                .then(CommandManager.literal("help").executes(ctx -> {

                    ctx.getSource().sendMessage(Text.literal("§6=== Inventory Plus Help ==="));
                    ctx.getSource().sendMessage(Text.literal("§e/inventory-plus save §7→ Save current hotbar"));
                    ctx.getSource().sendMessage(Text.literal("§e/inventory-plus load §7→ Load selected hotbar"));
                    ctx.getSource().sendMessage(Text.literal("§e/inventory-plus index set <0-7> §7→ Select page"));
                    ctx.getSource().sendMessage(Text.literal("§e/inventory-plus index get §7→ Show current page"));

                    return 1;
                }))));
    }
}