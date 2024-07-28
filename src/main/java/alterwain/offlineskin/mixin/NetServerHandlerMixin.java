package alterwain.offlineskin.mixin;

import alterwain.offlineskin.OfflineSkinMod;
import alterwain.offlineskin.SkinConfig;
import alterwain.offlineskin.SkinResponseHandler;
import alterwain.offlineskin.packet.Packet245SkinResponse;
import alterwain.offlineskin.packet.Packet246SkinSet;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetServerHandler.class)
public abstract class NetServerHandlerMixin extends net.minecraft.core.net.handler.NetHandler implements net.minecraft.core.net.ICommandListener,
        SkinResponseHandler {
    @Shadow(remap = false)
    private net.minecraft.server.MinecraftServer mcServer;

    @Override
    public void offlineSkinChanger$handleSkinResponse(Packet245SkinResponse response) {
        OfflineSkinMod.skins.put(response.getUsername(), new SkinConfig(
                OfflineSkinMod.bytesToImage(response.getSkin()),
                OfflineSkinMod.bytesToImage(response.getCape()),
                response.isModelType()
        ));
        for( int i = 0; i < this.mcServer.playerList.playerEntities.size(); i++ ) {
            this.mcServer.playerList.playerEntities.get(i)
                    .playerNetServerHandler
                    .sendPacket(new Packet246SkinSet(response.getUsername(), response.getSkin(), response.getCape(), response.isModelType()));
        }
    }
}
