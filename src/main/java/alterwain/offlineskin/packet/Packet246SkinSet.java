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
import java.util.HashMap;
import java.util.Map;

public class Packet246SkinSet extends Packet {

	private static final Map<String, SendSet> skinThread = new HashMap<>();
	private static final Map<String, SendSet> capeThread = new HashMap<>();
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
		if( Packet246SkinSet.skinThread.containsKey(this.username) ) {
			Packet246SkinSet.skinThread.get(this.username).setRunning(false);
		}
		SendSet ss = new SendSet(this.username, false);
        Packet246SkinSet.skinThread.put(this.username, ss);
		ss.start();
		if( Packet246SkinSet.capeThread.containsKey(this.username) ) {
			Packet246SkinSet.capeThread.get(this.username).setRunning(false);
		}
		SendSet cs = new SendSet(this.username, true);
        Packet246SkinSet.capeThread.put(this.username, cs);
		cs.start();
    }

    @Override
    public int getPacketSize() {
        return skin.length+cape.length+username.length()+13;
    }
}
