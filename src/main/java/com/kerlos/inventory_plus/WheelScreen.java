package com.kerlos.inventory_plus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class WheelScreen extends Screen {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private static final int NUM_PAGES = 8;
    private static final int RADIUS = 60;
    private static final int WHEEL_SIZE = 180;

    private final MultiHotbarManager hotbarManager = InventoryPlus.HOTBAR_MANAGER;

    public WheelScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;

        // Initialiser toutes les pages avec l’inventaire actuel
        if (hotbarManager.getCurrentPage() == null) {
            hotbarManager.initFromPlayer();
        }

        // Boutons de la roue : sélectionner l’index de page
        double step = 2 * Math.PI / NUM_PAGES;
        for (int i = 0; i < NUM_PAGES; i++) {
            double angle = i * step - Math.PI / 2;
            int x = (int)(cx + Math.cos(angle) * RADIUS);
            int y = (int)(cy + Math.sin(angle) * RADIUS);
            int index = i;

            this.addDrawableChild(ButtonWidget.builder(
                    Text.of("" + (i)),
                    btn -> hotbarManager.setSelectedPage(index)
            ).dimensions(x - 15, y - 15, 30, 30).build());
        }

        // Bouton Sauvegarder
        this.addDrawableChild(ButtonWidget.builder(
                Text.of("Save"),
                btn -> hotbarManager.saveCurrentPage()
        ).dimensions(cx-25, cy -20, 50, 20).build());

        // Bouton Charger
        this.addDrawableChild(ButtonWidget.builder(
                Text.of("Load"),
                btn -> hotbarManager.loadPage(hotbarManager.getSelectedPage())
        ).dimensions(cx-25, cy, 50, 20).build());

        // Bouton Retour
        this.addDrawableChild(ButtonWidget.builder(
                Text.of("Back"),
                btn -> mc.gameRenderer.getClient().setScreen(null)
        ).dimensions(10, 10, 40, 20).build());
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
        int cx = this.width / 2;
        int cy = this.height / 2;

        // Dessiner la roue
//        Identifier texture = Identifier.of("inventory-plus", "textures/gui/wheel.png");
//        graphics.drawTexture(
//                RenderPipelines.GUI_TEXTURED,
//                texture,
//                cx - WHEEL_SIZE / 2,
//                cy - WHEEL_SIZE / 2,
//                0f, 0f,
//                WHEEL_SIZE, WHEEL_SIZE,
//                WHEEL_SIZE, WHEEL_SIZE
//        );

        // Dessiner les rectangles de chaque segment
        double step = 2 * Math.PI / NUM_PAGES;
        for (int i = 0; i < NUM_PAGES; i++) {
            double angle = i * step - Math.PI / 2;
            int x = (int)(cx + Math.cos(angle) * RADIUS);
            int y = (int)(cy + Math.sin(angle) * RADIUS);

            int color;
            if (i == hotbarManager.getCurrentPageIndex()) color = 0xAA00FF00; // vert = page active
            else if (i == hotbarManager.getSelectedPage()) color = 0xFFFFFF00; // jaune = sélectionnée
            else color = 0xAAFF0000; // rouge normal

            graphics.fill(x-18, y-18, x+18, y+18, color);
        }

        // Preview de la page sélectionnée en dessous de la roue
        drawHotbarPreview(graphics, cx, cy, hotbarManager.pages.get(hotbarManager.getSelectedPage()));

        // Dessiner les boutons par-dessus
        super.render(graphics, mouseX, mouseY, delta);
    }

    private void drawHotbarPreview(DrawContext ctx, int centerX, int centerY, HotbarPage page) {
        ItemStack[] hotbar = page.getAll();
        int size = 20;
        int spacing = 20;
        int startX = centerX - (hotbar.length * spacing) / 2;
        int y = centerY + WHEEL_SIZE / 2;

        ctx.fill(startX, y, startX + hotbar.length * spacing, y+size, 0x80000000);

        for (int i = 0; i < hotbar.length; i++) {
            drawItem(ctx, startX + i*spacing, y+2, hotbar[i]);
        }
    }

    private void drawItem(DrawContext ctx, int x, int y, ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;
        ctx.drawItem(stack, x, y);

        String text = String.valueOf(stack.getCount());
        int textWidth = mc.textRenderer.getWidth(text);

        if (stack.getCount() > 1)
            ctx.drawText(
                    mc.textRenderer,
                    text,
                    x + 9 - textWidth / 2,
                    y + 10,
                    0xFFFFFFFF,
                    true
            );
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}