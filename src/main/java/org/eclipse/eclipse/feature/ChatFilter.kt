package org.eclipse.eclipse.feature

import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.eclipse.eclipse.module.Handle
import org.eclipse.eclipse.util.noControlCodes
import org.eclipse.eclipse.util.Sender

class ChatFilter {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun onChatReceived(event: ClientChatReceivedEvent) {
        val message = event.message.unformattedText.noControlCodes.trim()

        if (Handle.isModuleEnabled("SkyBlock", "Chat Filter")) {
            // If the message is empty or only contains whitespace, cancel the event.
            if (message.isEmpty() || message.isBlank()) {
                event.setCanceled(true)
            }
        }
        if (Handle.isModuleEnabled("SkyBlock", "Chat Filter")) {
            // If the message is empty or only contains whitespace, cancel the event.
            if (message.isEmpty() || message.isBlank()) {
                event.setCanceled(true)
            }
        }
        // Material Stash Patterns
        val mpattern1 = Regex(""".*You have (\d{1,3}(,\d{3})*|0) material(s)? stashed away!.*""")
        val mpattern2 = Regex(""".*\(This totals (\d{1,3}(,\d{3})*|0) type(s)? of material(s)? stashed!\).*""")
        val mpattern3 = Regex(""".*>>> CLICK HERE to pick (them|it) up! <<<.*""")

        // Item Stash Patterns
        val ipattern1 = Regex(""".*You have (\d{1,3}(,\d{3})*|0) item(s)? stashed away!.*""")
        val ipattern2 = Regex(""".*>>> CLICK HERE to pick (them|it) up! <<<.*""")

        // Check for Material Stash
        if (mpattern1.matches(message) || mpattern2.matches(message) || mpattern3.matches(message)) {
            if (Handle.isModuleEnabled("SkyBlock", "Chat Filter")) {
                event.setCanceled(true)
            }
        }

        // Check for Item Stash
        if (ipattern1.matches(message) || ipattern2.matches(message)) {
            if (Handle.isModuleEnabled("SkyBlock", "Chat Filter")) { // Assuming there's a similar configuration for item stash
                event.setCanceled(true)
            }
        }
    }
}