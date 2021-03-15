package chatting.application.Server;

import javax.lang.model.element.ModuleElement;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;


public class Server implements  Runnable{

    Socket socket;
    public static Vector clients = new Vector();

    public Server(Socket socket){
        this.socket = socket;
    }
    public static void main(String[] args){
        try (ServerSocket serverSocket = new ServerSocket(2021)) {
            while(true)
                try{
                Socket skt = serverSocket.accept();
                Server server = new Server(skt);
                Thread t1 = new Thread(server);
                t1.start();

            } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }catch(Exception e){
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void run() {
        try {
            DataInputStream din = new DataInputStream(this.socket.getInputStream());
            DataOutputStream dout = new DataOutputStream(this.socket.getOutputStream());
            clients.add(dout);
            while(true){
                String data = din.readUTF();
                System.out.println(data);

                for(int i=0;i<clients.size();i++){
                    try{
                        DataOutputStream dTemp = (DataOutputStream) clients.get(i);
                        dTemp.writeUTF(data);
                        dTemp.flush();
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }



            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
