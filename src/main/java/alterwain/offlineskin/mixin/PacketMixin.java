package alterwain.offlineskin.mixin;

import alterwain.offlineskin.packet.Packet244SkinRequest;
import alterwain.offlineskin.packet.Packet245SkinResponse;
import alterwain.offlineskin.packet.Packet246SkinSet;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Packet.class)
public abstract class PacketMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyPacketsTable(CallbackInfo ci) {
        Packet.addIdClassMapping(244, true, false, Packet244SkinRequest.class);
        Packet.addIdClassMapping(245, false, true, Packet245SkinResponse.class);
        Packet.addIdClassMapping(246, true, false, Packet246SkinSet.class);
    }
}
