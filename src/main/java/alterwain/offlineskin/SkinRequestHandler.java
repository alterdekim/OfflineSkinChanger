package alterwain.offlineskin;


import alterwain.offlineskin.packet.Packet244SkinRequest;

public interface SkinRequestHandler {
    void offlineSkinChanger$handleSkinRequest(Packet244SkinRequest request);
}
