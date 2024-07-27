package alterwain.offlineskin.mixin;

import alterwain.offlineskin.OfflineSkinMod;
import alterwain.offlineskin.packet.Packet244SkinRequest;
import alterwain.offlineskin.packet.Packet245SkinResponse;
import alterwain.offlineskin.SkinRequestHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.handler.NetHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetClientHandler.class)
public abstract class NetClientHandlerMixin extends NetHandler implements SkinRequestHandler {
    @Shadow @Final private NetworkManager netManager;

    @Shadow @Final private Minecraft mc;

    @Override
    public void offlineSkinChanger$handleSkinRequest(Packet244SkinRequest request) {
        this.netManager.addToSendQueue(new Packet245SkinResponse(mc.thePlayer.username,
                OfflineSkinMod.imageToBytes(OfflineSkinMod.skinImage),
                OfflineSkinMod.imageToBytes(OfflineSkinMod.capeImage),
                false));
    }
}
