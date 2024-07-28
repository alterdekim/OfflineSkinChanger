package alterwain.offlineskin.mixin;

import alterwain.offlineskin.ForceDownloadHandler;
import alterwain.offlineskin.OfflineSkinMod;
import net.minecraft.client.render.DownloadedTexture;
import net.minecraft.client.render.ImageParser;
import net.minecraft.client.render.RenderEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.image.BufferedImage;
import java.util.Map;

@Mixin(RenderEngine.class)
public abstract class RenderEngineMixin implements ForceDownloadHandler {

    @Shadow(remap = false)
    private Map<String, DownloadedTexture> downloadedTextures;

    @Shadow(remap = false)
    public abstract int allocateAndSetupTexture(BufferedImage bufferedimage);

    @Shadow(remap = false)
    public abstract void bindTexture(int i);

    @Shadow(remap = false)
    public abstract int getTexture(String name);

    @Override
    public boolean offlineSkinChanger$forceLoadDownloadableTexture(String url, String localTexture, ImageParser imageParser) {
        DownloadedTexture texture = new DownloadedTexture(null, imageParser);
        if( url.startsWith("offlineSkinLocal") ) {
            if( OfflineSkinMod.skins.containsKey(url.substring(17)) ) {
                texture.image = OfflineSkinMod.skins.get(url.substring(17)).getSkin();
            } else {
                texture.image = OfflineSkinMod.skinImage;
            }
        } else if( url.startsWith("offlineCapeLocal") ) {
            if( OfflineSkinMod.skins.containsKey(url.substring(17))) {
                texture.image = OfflineSkinMod.skins.get(url.substring(17)).getCape();
            } else {
                texture.image = OfflineSkinMod.capeImage;
            }
        } else {
            texture = new DownloadedTexture(url, imageParser);
        }
        this.downloadedTextures.put(url, texture);


        if (texture.textureId < 0 && texture.image != null) {
            texture.textureId = this.allocateAndSetupTexture(texture.image);
        }

        if (texture.textureId > 0) {
            this.bindTexture(texture.textureId);
            return true;
        } else {
            return false;
        }
    }

    public boolean loadDownloadableTexture(String url, String localTexture, ImageParser imageParser) {
        if (url == null) {
            if (localTexture != null) {
                this.bindTexture(this.getTexture(localTexture));
                return true;
            } else {
                return false;
            }
        } else {
            DownloadedTexture texture = this.downloadedTextures.get(url);
            if (texture == null) {
                texture = new DownloadedTexture(null, imageParser);
                if( url.startsWith("offlineSkinLocal") ) {
					if( OfflineSkinMod.skins.containsKey(url.substring(17)) ) {
						texture.image = OfflineSkinMod.skins.get(url.substring(17)).getSkin();
					} else {
						texture.image = OfflineSkinMod.skinImage;
					}
                } else if( url.startsWith("offlineCapeLocal") ) {
					if( OfflineSkinMod.skins.containsKey(url.substring(17))) {
						texture.image = OfflineSkinMod.skins.get(url.substring(17)).getCape();
					} else {
						texture.image = OfflineSkinMod.capeImage;
					}
                } else {
                    texture = new DownloadedTexture(url, imageParser);
                }
                this.downloadedTextures.put(url, texture);
            }

            if (texture.textureId < 0 && texture.image != null) {
                texture.textureId = this.allocateAndSetupTexture(texture.image);
            }

            if (texture.textureId > 0) {
                this.bindTexture(texture.textureId);
                return true;
            } else if (localTexture != null) {
                this.bindTexture(this.getTexture(localTexture));
                return true;
            } else {
                return false;
            }
        }
    }
}
