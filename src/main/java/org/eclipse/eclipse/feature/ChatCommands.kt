package org.eclipse.eclipse.feature
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.eclipse.eclipse.util.noControlCodes
import org.eclipse.eclipse.module.Handle
import java.util.*
class ChatCommands {
    private var lock = false
    private var xPass = false
    private var logMenu = false
    private var downtimer = ""
    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        if (Handle.isModuleEnabled("SkyBlock", "Chat Commands")) {
            val message = event.message.unformattedText.noControlCodes
            var rega = Regex("Party > (\\[.+\\])? ?(.+): (meow)$")
            if (rega.matches(message)) {
                val command = rega.matchEntire(message)?.groups?.get(3)?.value
                if (command.equals("meow")) {
                    rCmd("/pc meow")
                }
            }
            /*
            if (message.contains("> EXTRA STATS <")) {
                if (downtimer != "") {
                    downtimer = ""
                }
            }

             */
            if (Regex("(\\[.+])? ?(.+) has disbanded the party!").matches(message)) {
                lock = false
            } else {
                val reg = Regex("Party > (\\[.+])? ?(.+): \\?([^\\s]+)(?: ([^\\s]+))?$")
                if (reg.matches(message)) {
                    val matchResult = reg.matchEntire(message)
                    val sender = matchResult?.groups?.get(2)?.value
                    val command = matchResult?.groups?.get(3)?.value
                    val ign = matchResult?.groups?.get(4)?.value // IGN as the optional fourth parameter

                    cmdRun(sender, command, ign)
                }
            }
        }
    }
    private fun numToStr(num: Char): String {
        return when (num) {
            '0' -> "ENTRANCE"
            '1' -> "ONE"
            '2' -> "TWO"
            '3' -> "THREE"
            '4' -> "FOUR"
            '5' -> "FIVE"
            '6' -> "SIX"
            '7' -> "SEVEN"
            else -> ""
        }
    }
    private fun cmdRun(sender: String?, command: String?, ign: String?) {
        val floorCommandRegex = Regex("\\?(m|f)([0-7])")
        command?.let { cmd ->
            floorCommandRegex.matchEntire(cmd)?.let { matchResult ->
                val commandType = matchResult.groups[1]?.value // 'm' or 'f'
                val floorNumber = matchResult.groups[2]?.value?.firstOrNull() // Digit between 0 and 7
                rCmd("/pc COMMAND TYPE: $commandType, $floorNumber")
                floorNumber?.let {
                    val floorStr = numToStr(it)
                    val response = when (commandType) {
                        "m" -> "/joininstance MASTER_CATACOMBS_FLOOR_$floorStr"
                        "f" -> "/joininstance CATACOMBS_FLOOR_$floorStr"
                        else -> ""
                    }
                    rCmd(response)
                    return // Stop further processing
                }
            }
        }
        val firstWord = command?.split(" ")?.getOrNull(0) ?: return
        val slowLowUsers = arrayOf("Adenaaa13", "haquire", "Mimikyuuwo")
        when (firstWord) {
            "devhelp" -> if (ign == null) rCmd("/pc [eclipse] Developer CMDs: ?lock, ?ping, ?logmenu, ?m{floor}, ?f{floor}")
            "help" -> if (ign == null) rCmd("/pc [eclipse]: ?help, ?pt, ?warp, ?allinv, ?rq, ?dt, ?coords, ?kickoffline, ?inv, ?open, ?close")
            "pt" -> if (!lock && ign == null) {
                rCmd("/p transfer " + sender)
            }
            "warp" -> if (!lock && ign == null) rCmd("/p warp")
            "allinv" -> if (!lock && ign == null) rCmd("/p settings allinvite")
            "rq" -> if (!lock && ign == null) rCmd("/instancerequeue")
            "lock" -> if (lock && sender.equals(Minecraft.getMinecraft().thePlayer.name) && ign == null) {
                lock = false
                rCmd("/pc Unlocked Command Execution")
            } else if (!lock && sender.equals(Minecraft.getMinecraft().thePlayer.name) && ign == null) {
                lock = true
                rCmd("/pc Locked Command Execution")
            } else {
                rCmd("/pc You cannot use this command!")
            }
            "pass" -> if (sender.equals(Minecraft.getMinecraft().thePlayer.name) && ign == null && xPass == true) {
                xPass = false
                rCmd("/pc Unlocked Command Execution")
            } else if (sender.equals(Minecraft.getMinecraft().thePlayer.name) && ign == null && xPass == false) {
                xPass = true
                rCmd("/pc Locked Command Execution")
            } else {
                rCmd("/pc You cannot use this command!")
            }
            "dt" -> if (!lock && ign == null) downtimer = sender.toString()

            "coords" -> if (!lock && ign == null) {
                var a = Minecraft.getMinecraft().thePlayer.posX.toInt()
                var b = Minecraft.getMinecraft().thePlayer.posY.toInt()
                var c = Minecraft.getMinecraft().thePlayer.posZ.toInt()
                rCmd("/pc x: $a, y: $b, z: $c")
            }
            "kickoffline" -> if (!lock && ign == null) {
                rCmd("/p kickoffline")
            }
            "ping" -> if (!lock && sender in slowLowUsers && ign == null) {
                var ping = Minecraft.getMinecraft().netHandler.getPlayerInfo(Minecraft.getMinecraft().thePlayer.gameProfile.id)?.responseTime
                rCmd("/pc ${ping ?: "unknown"}ms")
            }
            "inv" -> if (!lock) {
                rCmd("/p $ign")
            }
            "logmenu" -> if (!lock && sender in slowLowUsers && ign == null) {
                logMenu = true
            }
            "open" -> if (!lock) {
                rCmd("/stream open $ign")
            }
            "close" -> if (!lock && ign == null) {
                rCmd("/stream close")
            }
            // m7
            "m1" -> if (!lock && ign == null) {
                rCmd("/joininstance master_catacombs_floor_one")
            }
            "m2" -> if (!lock && ign == null) {
                rCmd("/joininstance master_catacombs_floor_two")
            }
            "m3" -> if (!lock && ign == null) {
                rCmd("/joininstance master_catacombs_floor_three")
            }
            "m4" -> if (!lock && ign == null) {
                rCmd("/joininstance master_catacombs_floor_four")
            }
            "m5" -> if (!lock && ign == null) {
                rCmd("/joininstance master_catacombs_floor_five")
            }
            "m6" -> if (!lock && ign == null) {
                rCmd("/joininstance master_catacombs_floor_six")
            }
            "m7" -> if (!lock && ign == null) {
                rCmd("/joininstance master_catacombs_floor_seven")
            }
        }
    }

    private fun rCmd(st: String) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(st)
    }
}