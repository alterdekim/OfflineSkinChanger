package alterwain.offlineskin.mixin;

import alterwain.offlineskin.GuiSkinChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.*;
import net.minecraft.client.gui.options.data.OptionsPages;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsPages.class)
public abstract class OptionsPagesMixin {
    @Final
    @Shadow(remap = false)
    @Mutable
    private static Minecraft mc;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyGeneralScreen(CallbackInfo ci) {
        OptionsPages.GENERAL.withComponent(new ShortcutComponent("gui.options.page.general.button.edit_skin", () -> {
            mc.displayGuiScreen(new GuiSkinChanger(mc.currentScreen));
        }));
    }
}
