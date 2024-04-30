package org.eclipse.eclipse.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.eclipse.eclipse.setting.BooleanSetting;
import org.eclipse.eclipse.setting.ColorSetting;
import org.eclipse.eclipse.setting.DoubleSetting;
import org.eclipse.eclipse.setting.IntegerSetting;
import org.eclipse.eclipse.setting.Setting;
import org.eclipse.eclipse.setting.StringSetting;
import com.lukflug.panelstudio.setting.ICategory;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.IModule;
import scala.reflect.runtime.Settings;

public enum Category implements ICategory {
	SKYBLOCK("SkyBlock"),COMBAT("Combat"),HUD("HUD"),RENDER("Render");
	public final String displayName;
	public final List<Module> modules=new ArrayList<Module>();
	public static Random random=new Random();

	private Category (String displayName) {
		this.displayName=displayName;
	}

	public static void init() {
		Module ghostBlockModule = new Module("Ghost Block", "Ghost Block Generator", () -> true, true);
		ghostBlockModule.settings.add(new IntegerSetting("Distance", "Ghost Block Distance", "Sets the maximum distance for Ghost Block", () -> true, 1, 25, 50));
		ghostBlockModule.settings.add(new IntegerSetting("Speed", "Ghost Block Update Speed", "Sets how fast the Ghost Block updates", () -> true, 0, 1000, 5));
		Module chatFilterModule = new Module("Chat Filters", "Various chat filters for skyblock.", () -> true, true);
		chatFilterModule.settings.add(new BooleanSetting("Item Stash", "Item Stash Message Filter", "Hides the Item Stash Full message.", () -> true, false));
		chatFilterModule.settings.add(new BooleanSetting("Material Stash", "Material Stash Message Filter", "Hides the Material Stash Full message.", () -> true, false));
		Module dodgelistModule = new Module("DodgeList", "A dodgelist for the various partyfinders in skyblock.", () -> true, true);
		dodgelistModule.settings.add(new StringSetting("IGNs", "IGNs to be blacklisted.", "Separate IGNs with spaces.", () -> true, ""));
		SKYBLOCK.modules.add(dodgelistModule); // unimplemented
		SKYBLOCK.modules.add(ghostBlockModule);
		SKYBLOCK.modules.add(chatFilterModule); // unimplemented
		// RENDER
		Module playerEspModule = new Module("Player ESP", "Player ESP Toggle", () -> true, true);
		RENDER.modules.add(playerEspModule);
		// RENDER

		// COMBAT

		Module hitDelayFix = new Module("Hit Delay Fix", "Fixes the 10 tick delay in between some hits.", () -> true, true);
		COMBAT.modules.add(hitDelayFix); // unimplemented



		// END OF COMBAT

		Module chatCommandsModule = new Module("Chat Commands", "Party Chat Commands", () -> true, true);
		SKYBLOCK.modules.add(chatCommandsModule);
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
	@Override
	public Stream<IModule> getModules() {
		return modules.stream().map(module->module);
	}


	public static IClient getClient() {
		return new IClient() {
			@Override
			public Stream<ICategory> getCategories() {
				return Arrays.stream(Category.values());
			}
		};
	}

}
