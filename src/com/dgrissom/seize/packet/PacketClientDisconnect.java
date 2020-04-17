package com.dgrissom.seize.packet;

public class PacketClientDisconnect extends Packet {
    public PacketClientDisconnect() {
        super(Type.CLIENT_DISCONNECT);
    }

    @Override
    int getParamsCount() {
        return 0;
    }
    @Override
    void initParams(String[] params) {

    }
    @Override
    String[] serializeParams() {
        return new String[] {};
    }
}
