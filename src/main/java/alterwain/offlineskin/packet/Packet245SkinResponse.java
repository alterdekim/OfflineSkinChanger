package alterwain.offlineskin.packet;

import alterwain.offlineskin.SkinResponseHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet245SkinResponse extends Packet {
    private String username;
    private byte[] skin;
    private byte[] cape;
    private boolean modelType;

    public Packet245SkinResponse(String username, byte[] skin, byte[] cape, boolean modelType) {
		this.username = username;
        this.skin = skin;
        this.cape = cape;
        this.modelType = modelType;
    }

    public Packet245SkinResponse() {
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
        ((SkinResponseHandler) netHandler).offlineSkinChanger$handleSkinResponse(this);
    }

    public byte[] getSkin() {
        return skin;
    }

    public byte[] getCape() {
        return cape;
    }

    public boolean isModelType() {
        return modelType;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int getPacketSize() {
        return skin.length+cape.length+username.length()+13;
    }
}
