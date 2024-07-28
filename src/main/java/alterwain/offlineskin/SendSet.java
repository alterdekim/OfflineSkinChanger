package alterwain.offlineskin;

import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.PlayerSkinParser;

public class SendSet extends Thread {

    private final String username;
    private final boolean isCape;

    public SendSet(String username, boolean isCape) {
        this.username = username;
        this.isCape = isCape;
    }

    @Override
    public void run() {
            while(true) {
                try {
                    Thread.sleep(4000);
                    if (isCape) {
                        ((ForceDownloadHandler) EntityRenderDispatcher.instance.renderEngine).offlineSkinChanger$forceLoadDownloadableTexture("offlineCapeLocal:" + this.username, null, null);
                        return;
                    }
                    ((ForceDownloadHandler) EntityRenderDispatcher.instance.renderEngine).offlineSkinChanger$forceLoadDownloadableTexture("offlineSkinLocal:" + this.username, null, PlayerSkinParser.instance);
                    return;
                } catch (Exception e) {
					//e.printStackTrace();
                }
            }
    }
}
