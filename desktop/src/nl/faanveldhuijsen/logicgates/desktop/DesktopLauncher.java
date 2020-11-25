package nl.faanveldhuijsen.logicgates.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.faanveldhuijsen.logicgates.LogicGates;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		LogicGates listener = new LogicGates();

		listener.desktop = true;
		new LwjglApplication(listener, config);
	}
}
