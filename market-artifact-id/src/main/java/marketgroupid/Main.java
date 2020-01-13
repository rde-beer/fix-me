package marketgroupid;
import java.net.*; //Socket
import java.io.*; //Exception
import java.util.Scanner;

import marketgroupid.fix_message.*;

public class Main{
    public static Scanner scanner;
    public static Socket socket;
    public static Market market;
    public static void main(String[] args){
        try{
            socket = new Socket("localhost", 5001);
            scanner = new Scanner(System.in);

            boolean exit = false;
            String marketInput = null;
            while(!exit){
                System.out.println("Press 1 if you are the NYSE\nPress 2 if you are the LSE\nPress 3 if you are the JSE");
                marketInput = scanner.nextLine();
                switch(marketInput){
                    case("1"): market = new Market("NYSE"); exit = true; break;
                    case("2"): market = new Market("LSE"); exit = true; break;
                    case("3"): market = new Market("JSE"); exit = true; break;
                    default: break;
                }
            }
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(market.getMarketName()); printWriter.flush();

            while(true){

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputStreamLine = bufferedReader.readLine();
                if(inputStreamLine == null){System.out.println("\nRouter disconnected\n");break;}
                
                switch (fix_message.get_Instrument(inputStreamLine)) {
                    // THIS WILL PARSE THE FIX MESSAGE RECEIVED
                    case "1":
                        System.out.println("\nfix message market: "+inputStreamLine+" |again: "+inputStreamLine);
                        marketInput = market.decrementInstrument1Val(100);
                        printWriter.println(marketInput); printWriter.flush();
                        break;
                    case "2":
                        marketInput = market.decrementInstrument2Val(100);
                        printWriter.println(marketInput); printWriter.flush();
                        break;
                    case "3":
                        marketInput = market.decrementInstrument3Val(100);
                        printWriter.println(marketInput); printWriter.flush();
                        break;
                    default:
                        System.out.println("\nRouter\n" + inputStreamLine + "\n");
                        printWriter = new PrintWriter(socket.getOutputStream());
                        System.out.println(market.formattedList());
                        //marketInput = scanner.nextLine();
                        Thread.sleep(100);
                        printWriter.println(marketInput); printWriter.flush();
                        break;
                }

            }
        } catch (Exception e){System.err.println(e.getMessage());}
    }
}

class Market {
    private static String marketName;
    private static String instrument1Name;
    private static String instrument2Name;
    private static String instrument3Name;
    private static int instrument1Val;
    private static int instrument2Val;
    private static int instrument3Val;

    public Market(String string) {
        marketName = string;
        switch (string) {
            case "NYSE":
            instrument1Name = "Berkshire Hathaway";
            instrument2Name = "JP Morgan Chase & Co";
            instrument3Name = "Exxon Mobil";
            instrument1Val = 400;
            instrument2Val = 400;
            instrument3Val = 400;
                break;
            case "LSE":
            instrument1Name = "Royal Dutch Shell";
            instrument2Name = "HSBC Holdings";
            instrument3Name = "BP";
            instrument1Val = 5000;
            instrument2Val = 5000;
            instrument3Val = 5000;
                break;
            case "JSE":
            instrument1Name = "Investec";
            instrument2Name = "Sasol";
            instrument3Name = "Naspers";
            instrument1Val = 5000;
            instrument2Val = 5000;
            instrument3Val = 5000;    
                break;
            default:
                break;
        }
    }
    public String getInstrument1Name(){return instrument1Name;}
    public String getInstrument2Name(){return instrument2Name;}
    public String getInstrument3Name(){return instrument3Name;}
    public int getInstrument1Val(){return instrument1Val;}
    public int getInstrument2Val(){return instrument2Val;}
    public int getInstrument3Val(){return instrument3Val;}
    public String decrementInstrument1Val(int n){if (instrument1Val >= 100){instrument1Val -= n;return("purchase successful");}else{return("purchase unsuccessful");}}
    public String decrementInstrument2Val(int n){if (instrument2Val >= 100){instrument2Val -= n;return("purchase successful");}else{return("purchase unsuccessful");}}
    public String decrementInstrument3Val(int n){if (instrument3Val >= 100){instrument3Val -= n;return("purchase successful");}else{return("purchase unsuccessful");}}
/* 
    public String decrementInstrument2Val(int n){ instrument2Val -= n; return("purchase successful");}
    public String decrementInstrument3Val(int n){ instrument3Val -= n; return("purchase successful");} */
    public String getMarketName(){return marketName;}
    public String formattedList(){return getMarketName() + " instruments : " + 
        getInstrument1Name() + ": " + Integer.toString(getInstrument1Val()) +", " +
        getInstrument2Name() + ": " + Integer.toString(getInstrument2Val()) +", " +
        getInstrument3Name() + ": " + Integer.toString(getInstrument3Val());}
}
