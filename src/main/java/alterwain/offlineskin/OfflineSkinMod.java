package alterwain.offlineskin;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class OfflineSkinMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "offlineskin";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static File configPath = new File(Global.accessor.getMinecraftDir(), "config/offlineskin");
	public static BufferedImage skinImage;
	public static BufferedImage capeImage;
	public static Boolean showCape = false;
	public static Boolean skinModel = false;
	public static final Map<String, SkinConfig> skins = new HashMap<>();

    @Override
    public void onInitialize() {
        LOGGER.info("OfflineSkins initialized.");
    }

	@Override
	public void beforeGameStart() {
		configPath.mkdirs();
		try {
			if(!new File(configPath, "pref.conf").exists()) {
				writeConfig();
			} else {
				readConfig();
			}
			if(!new File(configPath, "skin.png").exists()) {
				BufferedImage i = ImageIO.read(Objects.requireNonNull(OfflineSkinMod.class.getClassLoader().getResourceAsStream("char.png")));
				ImageIO.write(i, "png", new File(configPath, "skin.png"));
			}
			skinImage = ImageIO.read(new File(configPath, "skin.png"));
			if (new File(configPath, "cape.png").exists()) {
				capeImage = ImageIO.read(new File(configPath, "cape.png"));
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	public static File chooseFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileFilter filter = new FileNameExtensionFilter("PNG File","png");
		fileChooser.addChoosableFileFilter(filter);
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	public static BufferedImage bytesToImage(byte[] b) {
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(b);
			return ImageIO.read(is);
		} catch (IOException e) {
			return null;
		}
	}

	public static byte[] imageToBytes(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
		} catch (IOException e) {
			return new byte[1];
		}
		return baos.toByteArray();
	}

	public static void writeConfig() {
		try {
			Files.write(new File(configPath, "pref.conf").toPath(), (showCape + ";" + skinModel).getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readConfig() {
		try {
			String s = new String(Files.readAllBytes(new File(configPath, "pref.conf").toPath()));
			String[] args = s.split(";");
			if( args.length != 2 ) return;
			showCape = Boolean.parseBoolean(args[0]);
			skinModel = Boolean.parseBoolean(args[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
