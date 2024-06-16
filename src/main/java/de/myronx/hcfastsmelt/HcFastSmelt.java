package de.myronx.hcfastsmelt;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class HcFastSmelt implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("hcfastsmelt");
    public static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {

        keyBinding = new KeyBinding(
                "key.hcfastsmelt.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_END,
                "category.hcfastsmelt.main"
        );

        KeyBindingHelper.registerKeyBinding(keyBinding);
        LOGGER.info("HcFastSmelt Mod started");
    }

    public static void clickSlots() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.interactionManager == null || client.player.currentScreenHandler == null) {
            return;
        }

        int syncId = client.player.currentScreenHandler.syncId;
        int slotCount = client.player.currentScreenHandler.slots.size();

        for (int i = 54; i <= 89 && i < slotCount; i++) {
            client.interactionManager.clickSlot(
                    syncId,
                    i,
                    0,
                    SlotActionType.PICKUP,
                    client.player
            );
        }
    }
}