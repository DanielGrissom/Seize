package com.dgrissom.seize.packet;

public class PacketServerClose extends Packet {
    public PacketServerClose() {
        super(Type.SERVER_CLOSE);
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
