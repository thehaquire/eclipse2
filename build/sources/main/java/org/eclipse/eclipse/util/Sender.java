package org.eclipse.eclipse.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Sender {
    public static void send(String message) {
        // Ensure that we are on the client side and the player is not null
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null) {
            ChatComponentText chatComponent = new ChatComponentText(message);
            mc.thePlayer.addChatMessage(chatComponent);
        }
    }
}