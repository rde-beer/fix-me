package routergroupid;
import java.net.*;

import routergroupid.fix_message.fix_message;

import java.io.*;

public class Main{
    public static Socket nyseSocket;
    public static Socket lseSocket;
    public static Socket jseSocket;
    public static void main(String[] args){
        try{
            Thread brokerThread = new Thread(new SuperHandler(new ServerSocket(5000)));brokerThread.start();
            Thread marketThread = new Thread(new SuperHandler(new ServerSocket(5001)));marketThread.start();
        } catch(IOException e){System.err.println(e.getMessage());}
    }
}
class SuperHandler implements Runnable{
    ServerSocket serverSocket;
    SuperHandler(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        System.out.println("Listener started on: " + serverSocket.getLocalPort());
    }
    @Override
    public void run() {
        try{
            while(true){        
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new SubHandler(socket));
                thread.start();
            }
        } catch (IOException e){System.err.println(e.getMessage());}
    }
}
class SubHandler implements Runnable{
    private Socket socket;
    private String uniqueID;
    SubHandler(Socket socket){
        this.socket = socket;
        this.uniqueID = socket.toString();
    }
    @Override
    public void run(){

        try{
            System.out.println("\nClient connected:\n" + uniqueID + "\n");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputStreamLine = bufferedReader.readLine();
            if(inputStreamLine == null){System.out.println("\nClient disconnected:\n" + uniqueID + "\n");}
            if(socket.getLocalPort() == 5001){
                switch (inputStreamLine){
                    case "NYSE": Main.nyseSocket = this.socket; break;
                    case "LSE": Main.lseSocket = this.socket; break;
                    case "JSE": Main.jseSocket = this.socket; break;
                    default: break;
                }
            }
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Hit enter to refresh");printWriter.flush();

            while(true){

                //READING
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                inputStreamLine = bufferedReader.readLine();
                if(inputStreamLine == null){System.out.println("\nClient disconnected:\n" + uniqueID + "\n");break;} //this breaks the loop
                System.out.println("\n" + uniqueID + "\n" + inputStreamLine + "\n");

                //WRITING
                printWriter = new PrintWriter(socket.getOutputStream());                    
                if(socket.getLocalPort() == 5000){
                    //switch(fix_message.get_Market(inputStreamLine)){
                    switch(fix_message.get_Market(inputStreamLine)){
                    //THIS IS WHERE THE FIX MESSAGE NEEDS TO BE PARSED AND SENT TO THE MARKET
                        case "1":
                            printWriter = new PrintWriter(Main.nyseSocket.getOutputStream());
                            System.out.println("fix message router: "+inputStreamLine);
                            // printWriter.println("1");printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            printWriter.println(inputStreamLine);printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.nyseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "2":
                            printWriter = new PrintWriter(Main.nyseSocket.getOutputStream());
                            // printWriter.println("2");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.nyseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break; 
                        case "3":   
                            printWriter = new PrintWriter(Main.nyseSocket.getOutputStream());
                            // printWriter.println("3");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.nyseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "4":
                            printWriter = new PrintWriter(Main.lseSocket.getOutputStream());
                            // printWriter.println("1");printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            printWriter.println(inputStreamLine);printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.lseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "5":
                            printWriter = new PrintWriter(Main.lseSocket.getOutputStream());
                            // printWriter.println("2");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.lseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break; 
                        case "6":   
                            printWriter = new PrintWriter(Main.lseSocket.getOutputStream());
                            // printWriter.println("3");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.lseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "7":
                            printWriter = new PrintWriter(Main.jseSocket.getOutputStream());
                            // printWriter.println("1");printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            printWriter.println(inputStreamLine);printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.jseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "8":
                            printWriter = new PrintWriter(Main.jseSocket.getOutputStream());
                            // printWriter.println("2");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.jseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break; 
                        case "9":   
                            printWriter = new PrintWriter(Main.jseSocket.getOutputStream());
                            // printWriter.println("3");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.jseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        default:    printWriter.println("Press 1 to view available markets");printWriter.flush(); break;
                    }
                    
                }else if(socket.getLocalPort() == 5001){
                    printWriter.println("Hit enter to refresh");printWriter.flush();
                }
            }
        } catch(IOException e){System.err.println(e.getMessage());}
    }
}

/*
System.out.println("Listener started on: \n" + 
serverSocket.getLocalPort() + "\n" +
serverSocket.getInetAddress() + "\n" +
serverSocket.getLocalSocketAddress() + "\n");

System.out.println("Client connected: \n" +
socket.getInetAddress() + "\n" +
socket.getLocalAddress() + "\n" +
socket.getLocalPort() + "\n" +
socket.getLocalSocketAddress() + "\n" +
socket.getPort() + "\n" +
socket.getRemoteSocketAddress() + "\n" +
socket.isBound() + "\n" +
socket.isClosed() + "\n" +
socket.isConnected() + "\n" +
socket.isInputShutdown() + "\n" +
socket.isOutputShutdown());
 */


// 0000;1;6;0