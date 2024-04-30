package org.eclipse.eclipse;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import org.eclipse.eclipse.module.Category;
import org.eclipse.eclipse.module.ClickGUIModule;
import org.eclipse.eclipse.module.HUDEditorModule;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import org.eclipse.eclipse.feature.ChatCommands;
import org.eclipse.eclipse.feature.GhostBlock;
import org.eclipse.eclipse.feature.*;

@Mod(modid=Eclipse.MODID,name=Eclipse.NAME,version=Eclipse.VERSION)
public class Eclipse {
    public static final String MODID="eclipse";
    public static final String NAME="eclipse";
    public static final String VERSION="0.1.0";
    public static Logger logger;
    private static ClickGUI gui;

    @EventHandler
    public void init (FMLInitializationEvent event) {
        reg(new GhostBlock());
        reg(new ChatCommands());
        reg(new PlayerESP());
        Category.init();
        gui=new ClickGUI();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender (RenderGameOverlayEvent.Post event) {
        if (event.type==RenderGameOverlayEvent.ElementType.HOTBAR) gui.render();
    }

    @SubscribeEvent
    public void onKeyInput (KeyInputEvent event) {
        if (Keyboard.isKeyDown(ClickGUIModule.keybind.getKey())) gui.enterGUI();
        if (Keyboard.isKeyDown(HUDEditorModule.keybind.getKey())) gui.enterHUDEditor();
        if (Keyboard.getEventKeyState()) gui.handleKeyEvent(Keyboard.getEventKey());
    }
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientRegistry.registerKeyBinding(GhostBlock.Companion.getGHOST_KEY());
        logger = event.getModLog();
    }
    private void reg(Object object) {
        MinecraftForge.EVENT_BUS.register(object);
    }

}
