package com.pfl.tcpfx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;

public class TcpClientServer {
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static BufferedReader reader;
    private static BufferedWriter writer;

    private static int mPort;
    private static String mHost;
    private static boolean isServerMode;

    public static LinkedList<String> queueRx = new LinkedList<String>();

    private static Thread threadReader;
    private static Thread threadInitServer;

    public enum States {
        OFF,
        SERVER,
        CLIENT,
        ERROR
    }
    public static States state = States.OFF;

    private static Runnable runnableReader = () -> {
        try {
            while (true) {
                queueRx.add(reader.readLine());
                TcpClientServer.RxNotification.notifySubscribers();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private static Runnable runnableInitConnection = () -> {
        try {
            if (isServerMode) {
                serverSocket = new ServerSocket(mPort);
                socket = serverSocket.accept();
            } else {
                socket = new Socket(mHost, mPort);
            }

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Connected");
            threadReader = new Thread(runnableReader);
            threadReader.start();
        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }
    };




    public static void startServer(int port) {
        mPort = port;
        isServerMode = true;
        state = States.SERVER;
        threadInitServer = new Thread(runnableInitConnection);
        threadInitServer.start();
    }

    public static void startClient(String host, int port) {
        mHost = host;
        mPort = port;
        isServerMode = false;
        state = States.CLIENT;
        threadInitServer = new Thread(runnableInitConnection);
        threadInitServer.start();
    }

    public static void stop() {
        try {
            if(socket != null) {
                socket.close();
                socket = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Socket closing error");
        }
        try {
            if(reader != null) {
                reader.close();
                reader = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Reader closing error");
        }
        try {
            if(writer != null) {
                writer.close();
                writer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Writer closing error");
        }
        try {
            if(serverSocket != null) {
                serverSocket.close();
                serverSocket = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server socket closing error");
        }
    }

    public static void sendMessage(String msg) {
        try {
            writer.write(msg);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }





    public interface RxListener {
        void onRxMsg();
    }

    public static class RxNotification {
        private static ArrayList<RxListener> listeners = new ArrayList<RxListener>();

        public static void addListener(RxListener lisToAdd) {
            listeners.add(lisToAdd);
        }

        public static void notifySubscribers() {
            for (RxListener rl : listeners) {
                rl.onRxMsg();
            }
        }
    }
}
