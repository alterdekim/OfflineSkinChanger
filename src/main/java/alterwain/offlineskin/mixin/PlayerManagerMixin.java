package alterwain.offlineskin.mixin;

import alterwain.offlineskin.SendInfo;
import alterwain.offlineskin.packet.Packet244SkinRequest;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.player.PlayerManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Shadow(remap = false)
    @Final
    public List<EntityPlayerMP> players;

    @Inject(method = "addPlayer", at = @At("HEAD"), remap = false)
    private void onAddPlayer(EntityPlayerMP player, CallbackInfo ci) {
        player.playerNetServerHandler.sendPacket(new Packet244SkinRequest(true, true, true));
        new SendInfo(player).start();
    }
}
