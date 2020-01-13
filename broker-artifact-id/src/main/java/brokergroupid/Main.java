package brokergroupid;
import java.net.*; //Socket
import java.io.*; //Exception
import java.util.Scanner;
import brokergroupid.fix_message.fix_message;

public class Main{
    public static Scanner scanner;
    public static Socket socket;
    public static void main(String[] args){
        try{
            socket = new Socket("localhost", 5000);
            scanner = new Scanner(System.in);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("I am a broker"); printWriter.flush();

            while(true){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputStreamLine = bufferedReader.readLine();
                if(inputStreamLine == null){System.out.println("\nServer disconnected\n");break;}
                System.out.println("\nServer\n" + inputStreamLine + "\n");
                printWriter = new PrintWriter(socket.getOutputStream());
                //System.out.println("Press 1 for NYSE instruments\nPress 2 for LSE instruments\nPress 3 for JSE instruments");
                System.out.println("Press number to buy stocks\nNYSE:\n1) 100 shares of Berkshire Hathaway\n2) 100 shares of JP Morgan Chase\n3) 100 shares of Exxon Mobil\n\nLSE:\n4) 100 shares of Royal Dutch Shell\n5) 100 shares of HSBC Holdings\n6) 100 shares of BP\n\nJSE:\n7) 100 shares of Investec\n8) 100 shares of Sasol\n9) 100 shares of Naspers\n");
                String brokerInput = scanner.nextLine();
                
                // id;instrument;quantity;market;price
                if (!brokerInput.isBlank()){
                    printWriter.println(fix_message.makefix("0001;1;100;"+brokerInput+";0")); printWriter.flush();
                } else
                    printWriter.println(fix_message.makefix("0001;1;100;0;0")); printWriter.flush();
                
                // THIS IS WHERE THE INPUT NEEDS TO BE TRANSFORMED INTO A FIX MESSAGE AND SENT TO THE ROUTER
                printWriter.println(brokerInput); printWriter.flush();
            }
        } catch (IOException e){System.err.println("broker: "+e.getMessage());}
    }
}
