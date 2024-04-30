package org.eclipse.eclipse.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Sender {
    public static void send(String message) {
        // Ensure that we are on the client side and the player is not null
        Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71439_g != null) {
            ChatComponentText chatComponent = new ChatComponentText(message);
            mc.field_71439_g.func_145747_a(chatComponent);
        }
    }
}