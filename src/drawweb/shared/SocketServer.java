package drawweb.shared;

import java.net.*;
import java.security.*;
import java.io.*;
import drawweb.events.*;

public class SocketServer extends Thread
{
    private boolean connect_status;
    public static int port;
    public static ServerSocket listenSock;
    public static DataInputStream in;
    public static DataOutputStream out;
    public static Socket sock;
    
    static {
        SocketServer.port = SocketConfig.port;
        SocketServer.listenSock = null;
        SocketServer.in = null;
        SocketServer.out = null;
        SocketServer.sock = null;
    }
    
    public SocketServer() {
        this.connect_status = false;
    }
    
    @Override
    public void run() {
        try {
            SocketServer.listenSock = new ServerSocket(SocketServer.port);
            while (true) {
                SocketServer.sock = SocketServer.listenSock.accept();
                SocketServer.in = new DataInputStream(SocketServer.sock.getInputStream());
                SocketServer.out = new DataOutputStream(SocketServer.sock.getOutputStream());
                this.connect_status = true;
                try {
                    if (SocketServer.in.readByte() == 1) {
                        final int random_code = new SecureRandom().nextInt();
                        SocketServer.out.writeInt(random_code);
                        final boolean success = UtilSocket.readString(SocketServer.in, false).equals(UtilSocket.hash(String.valueOf(random_code) + SocketConfig.password));
                        if (success) {
                            SocketServer.out.writeInt(1);
                            UtilSocket.createLog(SocketConfig.succesfullyLogin);
                        }
                        else {
                            SocketServer.out.writeInt(0);
                            UtilSocket.createLog(SocketConfig.wrongPassword);
                            this.connect_status = false;
                        }
                    }
                    else {
                        UtilSocket.createLog(SocketConfig.wrongData);
                        this.connect_status = false;
                    }
                    while (this.connect_status) {
                        final byte packetNumber = SocketServer.in.readByte();
                        if (packetNumber == 2) {
                            final String command = UtilSocket.readString(SocketServer.in, true);
                            final EventOutput event = UtilSocket.getManager().callOnCommandEvent(SocketServer.sock, command);
                            if (event.isCancelled()) {
                                SocketServer.out.writeInt(0);
                                SocketServer.out.flush();
                            }
                            else {
                                UtilSocket.sendCommand(event.getMessage(), SocketServer.out);
                            }
                        }
                        else if (packetNumber == 3) {
                            this.connect_status = false;
                        }
                        else if (packetNumber == 4) {
                            final String message = UtilSocket.readString(SocketServer.in, true);
                            final EventOutput event = UtilSocket.getManager().callOnMessageEvent(SocketServer.sock, message);
                            SocketServer.out.writeInt(event.isCancelled() ? 0 : 1);
                            SocketServer.out.flush();
                        }
                        else {
                            UtilSocket.createLog("Packet not found!");
                        }
                    }
                    SocketServer.out.flush();
                    SocketServer.out.close();
                    SocketServer.in.close();
                }
                catch (IOException ex) {
                    UtilSocket.createLog(ex.getMessage());
                    this.connect_status = false;
                }
                SocketServer.sock.close();
            }
        }
        catch (IOException ex) {
            UtilSocket.createLog(ex.getMessage());
        }
    }
}
