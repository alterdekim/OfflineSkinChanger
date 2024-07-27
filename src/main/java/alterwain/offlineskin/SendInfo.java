package alterwain.offlineskin;

import alterwain.offlineskin.packet.Packet246SkinSet;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class SendInfo extends Thread {

    private EntityPlayerMP player;

    public SendInfo(EntityPlayerMP player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            OfflineSkinMod.skins.keySet().stream().filter(k -> !k.equals(player.username))
                    .forEach(k -> player.playerNetServerHandler.sendPacket(new Packet246SkinSet(k,
                            OfflineSkinMod.imageToBytes(OfflineSkinMod.skins.get(k).getSkin()),
                            OfflineSkinMod.imageToBytes(OfflineSkinMod.skins.get(k).getCape()),
                            OfflineSkinMod.skins.get(k).getModelType()
                    )));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
