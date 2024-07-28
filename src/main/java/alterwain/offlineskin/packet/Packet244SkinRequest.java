package alterwain.offlineskin.packet;

import alterwain.offlineskin.SkinRequestHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet244SkinRequest extends Packet {

    private boolean requestSkin;
    private boolean requestCape;
    private boolean requestModelType;

    public Packet244SkinRequest() {}

    public Packet244SkinRequest(boolean requestSkin, boolean requestCape, boolean requestModelType) {
        this.requestSkin = requestSkin;
        this.requestCape = requestCape;
        this.requestModelType = requestModelType;
    }

    @Override
    public void readPacketData(DataInputStream dis) throws IOException {
        this.requestSkin = dis.readBoolean();
        this.requestCape = dis.readBoolean();
        this.requestModelType = dis.readBoolean();
    }

    @Override
    public void writePacketData(DataOutputStream dos) throws IOException {
        dos.writeBoolean(this.requestSkin);
        dos.writeBoolean(this.requestCape);
        dos.writeBoolean(this.requestModelType);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((SkinRequestHandler) netHandler).offlineSkinChanger$handleSkinRequest(this);
    }

    @Override
    public int getPacketSize() {
        return 3;
    }

    public boolean isRequestSkin() {
        return requestSkin;
    }

    public boolean isRequestCape() {
        return requestCape;
    }

    public boolean isRequestModelType() {
        return requestModelType;
    }
}
