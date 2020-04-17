package com.dgrissom.seize.packet;

public class PacketServerVersion extends Packet {
    private String serverVersion;

    public PacketServerVersion() {
        this(null);
    }
    public PacketServerVersion(String serverVersion) {
        super(Type.SERVER_VERSION);
        this.serverVersion = serverVersion;
    }

    public String getServerVersion() {
        return this.serverVersion;
    }

    @Override
    int getParamsCount() {
        return 1;
    }
    @Override
    void initParams(String[] params) {
        this.serverVersion = params[0];
    }
    @Override
    String[] serializeParams() {
        return new String[] {this.serverVersion};
    }
}
