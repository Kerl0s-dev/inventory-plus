package com.kerlos.inventory_plus.utils;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class InputRegistry {

    public static KeyBinding toggleHudKey;
    private static final KeyBinding.Category CATEGORY = KeyBinding.Category.create(Identifier.of("inventory-plus", "title"));

    public static void register() {
        // Affiche la roue
        toggleHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.inventory-plus.toggleHud",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                CATEGORY
        ));

    }
}
