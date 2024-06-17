package de.myronx.hcfastsmelt;

import de.myronx.hcfastsmelt.config.HcFastSmeltConfig;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class HcFastSmelt implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("hcfastsmelt");
    public static KeyBinding keyBinding;
    public static KeyBinding toggleHotbarKey;

    @Override
    public void onInitializeClient() {

        HcFastSmeltConfig.load();

        keyBinding = new KeyBinding(
                "key.hcfastsmelt.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DELETE,
                "category.hcfastsmelt.main"
        );

        KeyBindingHelper.registerKeyBinding(keyBinding);

        toggleHotbarKey = new KeyBinding(
                "key.hcfastsmelt.togglehotbar",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_UP,
                "category.hcfastsmelt.main"
        );

        KeyBindingHelper.registerKeyBinding(toggleHotbarKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleHotbarKey.wasPressed()) {
                HcFastSmeltConfig.hotbartoggle = !HcFastSmeltConfig.hotbartoggle;
                MinecraftClient.getInstance().inGameHud.setOverlayMessage(
                        Text.translatable("key.hcfastsmelt.infotext." + (HcFastSmeltConfig.hotbartoggle ? "on" : "off")),
                        false
                );
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            HcFastSmeltConfig.save();
            LOGGER.info("HcFastSmelt config saved");
        }));
        LOGGER.info("HcFastSmelt mod started");
    }

    public static void clickSlots(int maxSlot) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.interactionManager == null || client.player.currentScreenHandler == null) {
            return;
        }

        int syncId = client.player.currentScreenHandler.syncId;
        int slotCount = client.player.currentScreenHandler.slots.size();

        for (int i = 54; i <= maxSlot && i < slotCount; i++) {
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