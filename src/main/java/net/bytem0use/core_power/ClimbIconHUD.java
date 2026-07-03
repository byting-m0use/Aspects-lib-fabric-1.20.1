package net.bytem0use.core_power;

import com.mojang.blaze3d.systems.RenderSystem;
import net.bytem0use.Aspects;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

public class ClimbIconHUD implements HudRenderCallback {
    private static final Identifier CLIMBING_ENABLED = new Identifier(Aspects.MOD_ID,
            "textures/ability/climbing_ability.png");

    @Override
    public void onHudRender(DrawContext drawContext, float v) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;
        }

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, CLIMBING_ENABLED);
            for (int i = 0; i < 10; i++) {
                drawContext.drawTexture(CLIMBING_ENABLED, x - 94 + (i * 9), y - 54, 0, 0, 12, 12,
                        12, 12);
            }
    }
}
