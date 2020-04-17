package com.dgrissom.seize.packet;

// after a user selects their user
public class PacketClientLogin extends Packet {
    private String username;

    public PacketClientLogin() {
        this(null);
    }
    public PacketClientLogin(String username) {
        super(Type.CLIENT_LOGIN);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    int getParamsCount() {
        return 1;
    }
    @Override
    void initParams(String[] params) {
        this.username = params[0];
    }
    @Override
    String[] serializeParams() {
        return new String[] {this.username};
    }
}
