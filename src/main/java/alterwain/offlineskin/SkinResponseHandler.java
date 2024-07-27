package alterwain.offlineskin;

import alterwain.offlineskin.packet.Packet245SkinResponse;

public interface SkinResponseHandler {
    void offlineSkinChanger$handleSkinResponse(Packet245SkinResponse response);
}
