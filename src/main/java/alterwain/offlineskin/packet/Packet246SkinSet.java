package alterwain.offlineskin.packet;

import alterwain.offlineskin.OfflineSkinMod;
import alterwain.offlineskin.SendSet;
import alterwain.offlineskin.SkinConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet246SkinSet extends Packet {

    private String username;
    private byte[] skin;
    private byte[] cape;
    private boolean modelType;

    public Packet246SkinSet(String username, byte[] skin, byte[] cape, boolean modelType) {
        this.username = username;
        this.skin = skin;
        this.cape = cape;
        this.modelType = modelType;
    }

    public Packet246SkinSet() {
        this.username = "";
        this.skin = new byte[0];
        this.cape = new byte[0];
        this.modelType = false;
    }

    @Override
    public void readPacketData(DataInputStream dis) throws IOException {
		int nameLen = dis.readInt();
		byte[] ub = new byte[nameLen];
		dis.read(ub);
		this.username = new String(ub);
		int skinLen = dis.readInt();
		this.skin = new byte[skinLen];
		dis.read(this.skin);
		int capeLen = dis.readInt();
		this.cape = new byte[capeLen];
		dis.read(cape);
		this.modelType = dis.readBoolean();
    }

    @Override
    public void writePacketData(DataOutputStream dos) throws IOException {
		dos.writeInt(username.length());
		dos.write(username.getBytes());
		dos.writeInt(skin.length);
		dos.write(skin);
		dos.writeInt(cape.length);
		dos.write(cape);
		dos.writeBoolean(modelType);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        BufferedImage skin1 = OfflineSkinMod.bytesToImage(this.skin);
        BufferedImage cape1 = OfflineSkinMod.bytesToImage(this.cape);
        OfflineSkinMod.skins.put(this.username, new SkinConfig(skin1, cape1, this.modelType));
        new SendSet(this.username, false).start();
        new SendSet(this.username, true).start();
    }

    @Override
    public int getPacketSize() {
        return skin.length+cape.length+username.length()+13;
    }
}