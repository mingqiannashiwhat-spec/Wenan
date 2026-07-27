package com.ziad.pearlboost;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class PearlBoostClient implements ClientModInitializer {

    private static KeyBinding comboKey;
    private int tickCounter = -1;

    @Override
    public void onInitializeClient() {
        comboKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pearlboost.combo",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.pearlboost.binds"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (comboKey.wasPressed() && tickCounter == -1) {
                // الخانة 4 (Index 3)
                client.player.getInventory().selectedSlot = 3; 
                
                if (client.interactionManager != null) {
                    client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                }

                tickCounter = 0; 
            }

            if (tickCounter >= 0) {
                tickCounter++;
                
                // تأخير 2 ticks
                if (tickCounter == 2) {
                    // الخانة 8 (Index 7)
                    client.player.getInventory().selectedSlot = 7; 
                    
                    if (client.interactionManager != null) {
                        client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                    }
                    
                    tickCounter = -1; 
                }
            }
        });
    }
}
