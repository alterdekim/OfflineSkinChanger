package alterwain.offlineskin;

import net.minecraft.client.render.ImageParser;

public interface ForceDownloadHandler {
    boolean offlineSkinChanger$forceLoadDownloadableTexture(String url, String localTexture, ImageParser imageParser);
}
