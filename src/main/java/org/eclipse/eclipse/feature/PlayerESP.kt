package org.eclipse.eclipse.feature

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.util.*
import org.eclipse.eclipse.module.Handle
import org.lwjgl.opengl.GL11
import java.awt.*

import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class PlayerESP {

    @SubscribeEvent
    fun onRender(e: RenderWorldLastEvent) {
        if (Handle.isModuleEnabled("Render", "Player ESP")) {
            targetPlayers(e.partialTicks as Float)
        }
    }

    private fun targetPlayers(partial: Float) {
        val mc: Minecraft = Minecraft.getMinecraft()
        for (o in mc.theWorld.loadedEntityList) {
            val entity = o as Entity
            if (entity is EntityPlayer) {
                if (entity !== mc.thePlayer) {
                    entityESPBox(entity, partial, Color.CYAN)
                }
            }
        }
    }
    fun entityESPBox(entity: Entity, partialTicks: Float, color: Color) {
        GL11.glBlendFunc(770, 771)
        GL11.glEnable(3042)
        GL11.glLineWidth(1.5f)
        GL11.glDisable(3553)
        GL11.glDisable(2929)
        GL11.glDepthMask(false)
        setColor(color)
        RenderGlobal.drawSelectionBoundingBox(
            AxisAlignedBB(
                entity.getEntityBoundingBox().minX - entity.posX
                        + (entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks.toDouble()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosX), (
                        entity.getEntityBoundingBox().minY - entity.posY
                                + (entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks.toDouble()
                                - Minecraft.getMinecraft().getRenderManager().viewerPosY)), (
                        entity.getEntityBoundingBox().minZ - entity.posZ
                                + (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks.toDouble()
                                - Minecraft.getMinecraft().getRenderManager().viewerPosZ)), (
                        entity.getEntityBoundingBox().maxX - entity.posX
                                + (entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks.toDouble()
                                - Minecraft.getMinecraft().getRenderManager().viewerPosX)), (
                        entity.getEntityBoundingBox().maxY - entity.posY
                                + (entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks.toDouble()
                                - Minecraft.getMinecraft().getRenderManager().viewerPosY)), (
                        entity.getEntityBoundingBox().maxZ - entity.posZ
                                + (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks.toDouble()
                                - Minecraft.getMinecraft().getRenderManager().viewerPosZ))
            )
        )
        GL11.glEnable(3553)
        GL11.glEnable(2929)
        GL11.glDepthMask(true)
        GL11.glDisable(3042)
    }

    fun setColor(c: Color) {
        GL11.glColor4f(
            c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f,
            c.getAlpha() / 255f
        )
    }
}