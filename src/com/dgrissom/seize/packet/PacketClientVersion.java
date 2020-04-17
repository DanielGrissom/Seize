package com.dgrissom.seize.packet;

public class PacketClientVersion extends Packet {
    private String clientVersion;

    public PacketClientVersion() {
        this(null);
    }
    public PacketClientVersion(String clientVersion) {
        super(Type.CLIENT_VERSION);
        this.clientVersion = clientVersion;
    }

    public String getClientVersion() {
        return this.clientVersion;
    }

    @Override
    int getParamsCount() {
        return 1;
    }
    @Override
    void initParams(String[] params) {
        this.clientVersion = params[0];
    }
    @Override
    String[] serializeParams() {
        return new String[] {this.clientVersion};
    }
}
