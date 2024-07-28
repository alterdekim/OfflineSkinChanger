package alterwain.offlineskin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.core.lang.I18n;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static alterwain.offlineskin.OfflineSkinMod.configPath;

public class GuiSkinChanger extends GuiScreen {

    private GuiButton buttonLoadSkin;
    private GuiButton buttonLoadCape;
    private GuiButton buttonClose;
    private GuiButton modelType;
    private GuiButton hasCape;


    public GuiSkinChanger(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void init() {
        I18n stringtranslate = I18n.getInstance();
        this.controlList.clear();
        this.controlList.add(this.modelType = new GuiButton(3, this.width / 2 - 100, this.height / 4 + 2, 200, 20, stringtranslate.translateKey("gui.options.page.edit_skin.button.model_slim")));
        this.controlList.add(this.hasCape = new GuiButton(4, this.width / 2 - 100, this.height / 4 - 10 + 30 + 4, 200, 20, stringtranslate.translateKey("gui.options.page.edit_skin.button.has_cape_yes")));
        this.controlList.add(this.buttonLoadSkin = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringtranslate.translateKey("gui.options.page.edit_skin.button.load_skin")));
        this.controlList.add(this.buttonLoadCape = new GuiButton(1, this.width / 2 - 100, this.height / 4 - 10 + 50 + 18 + 20 + 4, 200, 20, stringtranslate.translateKey("gui.options.page.edit_skin.button.load_cape")));
        this.controlList.add(this.buttonClose = new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + 12,  stringtranslate.translateKey("gui.options.page.edit_skin.button.close")));
    }

    @Override
    protected void buttonPressed(GuiButton button) {
        try {
            if (button.enabled) {
                if (button.id == 2) {
                    this.mc.displayGuiScreen(this.getParentScreen());
                } else if (button.id == 0) { // skin
                    File skin = OfflineSkinMod.chooseFile();
                    if (skin != null) {
                        OfflineSkinMod.skinImage = ImageIO.read(skin);
                        Files.copy(skin.toPath(), new File(configPath, "skin.png").toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                } else if (button.id == 1) { // cape
                    File cape = OfflineSkinMod.chooseFile();
                    if (cape != null) {
                        OfflineSkinMod.capeImage = ImageIO.read(cape);
                        Files.copy(cape.toPath(), new File(configPath, "cape.png").toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                } else if(button.id == 3) { // model type

                } else if(button.id == 4) { // hasCape

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
