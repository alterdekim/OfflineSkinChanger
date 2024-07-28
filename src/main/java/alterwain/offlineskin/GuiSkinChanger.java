package alterwain.offlineskin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.lang.I18n;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static alterwain.offlineskin.OfflineSkinMod.*;

public class GuiSkinChanger extends GuiScreen {

    private GuiButton buttonLoadSkin;
    private GuiButton buttonLoadCape;
    private GuiButton buttonClose;
    private GuiButton modelType;
    private GuiButton hasCape;

	private I18n stringtranslate;


    public GuiSkinChanger(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void init() {
		OfflineSkinMod.readConfig();
		stringtranslate = I18n.getInstance();
        this.controlList.clear();
        this.controlList.add(this.modelType = new GuiButton(3, this.width / 2 - 100, this.height / 4 + 2, 200, 20, skinModel ? stringtranslate.translateKey("gui.options.page.edit_skin.button.model_slim") : stringtranslate.translateKey("gui.options.page.edit_skin.button.model_fat")));
        this.controlList.add(this.hasCape = new GuiButton(4, this.width / 2 - 100, this.height / 4 - 10 + 30 + 4, 200, 20, showCape ? stringtranslate.translateKey("gui.options.page.edit_skin.button.has_cape_yes") : stringtranslate.translateKey("gui.options.page.edit_skin.button.has_cape_no")));
        this.controlList.add(this.buttonLoadSkin = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringtranslate.translateKey("gui.options.page.edit_skin.button.load_skin")));
        this.controlList.add(this.buttonLoadCape = new GuiButton(1, this.width / 2 - 100, this.height / 4 - 10 + 50 + 18 + 20 + 4, 200, 20, stringtranslate.translateKey("gui.options.page.edit_skin.button.load_cape")));
        this.controlList.add(this.buttonClose = new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + 12,  stringtranslate.translateKey("gui.options.page.edit_skin.button.close")));
    }


	private void processSkin() {
		File skin = OfflineSkinMod.chooseFile();
		if (skin == null) return;
		try {
			OfflineSkinMod.skinImage = ImageIO.read(skin);
			Files.copy(skin.toPath(), new File(configPath, "skin.png").toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processCape() {
		File cape = OfflineSkinMod.chooseFile();
		if (cape == null) return;
		try {
			OfflineSkinMod.capeImage = ImageIO.read(cape);
			Files.copy(cape.toPath(), new File(configPath, "cape.png").toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processClose() {
		this.mc.displayGuiScreen(this.getParentScreen());
	}

	private void processModelType() {
		skinModel = !skinModel;
		this.modelType.displayString = skinModel ? stringtranslate.translateKey("gui.options.page.edit_skin.button.model_slim") : stringtranslate.translateKey("gui.options.page.edit_skin.button.model_fat");
		writeConfig();
	}

	private void processHasCape() {
		showCape = !showCape;
		this.hasCape.displayString = showCape ? stringtranslate.translateKey("gui.options.page.edit_skin.button.has_cape_yes") : stringtranslate.translateKey("gui.options.page.edit_skin.button.has_cape_no");
		writeConfig();
	}

    @Override
    protected void buttonPressed(GuiButton button) {
		if(!button.enabled) return;
		switch (button.id) {
			case 0:
				processSkin();
				break;
			case 1:
				processCape();
				break;
			case 2:
				processClose();
				break;
			case 3:
				processModelType();
				break;
			case 4:
				processHasCape();
				break;
		}
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        I18n stringtranslate = I18n.getInstance();
        this.drawDefaultBackground();
        this.drawStringCentered(this.fontRenderer, stringtranslate.translateKey("gui.options.page.edit_skin.label.title"), this.width / 2, this.height / 4 - 60 + 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTick);
    }
}
