package com.dgrissom.seize.packet;

// a chat message from the server to the clients
public class PacketServerMessage extends Packet {
    private String message;

    public PacketServerMessage() {
        this(null);
    }
    public PacketServerMessage(String message) {
        super(Type.SERVER_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    int getParamsCount() {
        return 1;
    }
    @Override
    void initParams(String[] params) {
        this.message = params[0];
    }
    @Override
    String[] serializeParams() {
        return new String[] {this.message};
    }
}
