package com.dgrissom.seize.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

// when extending packet you must supply a no-args constructor
// when the no-args constructor is called from here it is guaranteed to be followed by initParams
public abstract class Packet {
    private static final String PARAM_DELIMITER = String.valueOf('\0');

    public enum Type {
        CLIENT_DISCONNECT(PacketClientDisconnect.class),
        CLIENT_LOGIN(PacketClientLogin.class),
        CLIENT_VERSION(PacketClientVersion.class),
        SERVER_CLOSE(PacketServerClose.class),
        SERVER_MESSAGE(PacketServerMessage.class),
        SERVER_VERSION(PacketServerVersion.class),
        ;

        private final Class<? extends Packet> packetClass;

        Type(Class<? extends Packet> packetClass) {
            this.packetClass = packetClass;
        }

        public Class<? extends Packet> getPacketClass() {
            return this.packetClass;
        }
    }

    private final Type type;

    public Packet(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    // -1 for any amount
    abstract int getParamsCount();
    // receive. params.length is guaranteed to equal getParamsCount() unless it is -1 for any amount
    abstract void initParams(String[] params);
    // prepare to be sent
    abstract String[] serializeParams();

    private String serialize() {
        String[] params = serializeParams();

        StringBuilder serialized = new StringBuilder(String.valueOf(this.type.ordinal()));
        for (String param : params)
            serialized.append(PARAM_DELIMITER).append(param);
        return serialized.toString();
    }

    private static Packet deserialize(String in) {
        try {
            String[] split = in.split(PARAM_DELIMITER);
            int typeId = Integer.parseInt(split[0]);
            Type type = Type.values()[typeId];
            String[] params = (split.length == 1) ? new String[] {} : Arrays.copyOfRange(split, 1, split.length);

            Packet packet = type.getPacketClass().getDeclaredConstructor().newInstance();
            if (packet.getParamsCount() != params.length && packet.getParamsCount() != -1) {
                System.err.println("Invalid packet received!");
                return null;
            }

            packet.initParams(params);
            return packet;
        } catch (Exception e) {
            System.err.println("Invalid packet received!");
            e.printStackTrace();
            return null;
        }
    }


    public static void send(Packet packet, PrintWriter out) {
        out.println(packet.serialize());
    }
    public static Packet receive(String in) {
        if (in == null)
            return null;
        return deserialize(in);
    }
    @SuppressWarnings("unchecked")
    public static <T extends Packet> T nextPacket(BufferedReader in) throws IOException {
        try {
            return (T) receive(in.readLine());
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
}
