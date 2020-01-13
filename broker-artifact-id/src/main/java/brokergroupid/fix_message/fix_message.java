package brokergroupid.fix_message;

/**
 * ascii_op
 */
public class fix_message {

    private static long ascii_sum(String data){
        long tempsum = 0;
        int length = data.length();
        for (int i = 0; i < length ; i++) {
            // the '[space]' and '|' represent the start of header ascii which has a value of 1
            if (data.charAt(i) == ' ' || data.charAt(i) == '|')
                tempsum += 1;
            else
                tempsum += data.charAt(i);
        }
        return tempsum;
    }

    // the message_elements are as follows "ID;iNSTRUMENT;QUANTITY;MARKET;PRICE"
    // example of message_elements "0001;guitar;4;rando;10000"
    // example output "ID=0001|Instr=guitar|Quant=4|Market=rando|Price=10000|10=185"
    public static String makefix(String message_elements){
        String elements [] = message_elements.split(";");
        String fixmessage = 
        "ID="+elements[0]+
        "|Instr="+elements[1]+
        "|Quant="+elements[2]+
        "|Market="+elements[3]+
        "|Price="+elements[4]+"|";
        fixmessage += "10="+ascii_checksum(fixmessage);
        return fixmessage;
    }

    // This is just taking the full fixmessage recieved
    // it checking if the checksum matches up or not to the data contained
    public static boolean checksum_verification(String message){
        String elements [] = message.split("10=");
        if (ascii_checksum(elements[0]) != Integer.valueOf(elements[1]))
            return false;
        else 
            return true;
    }

    public static String get_ID(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            String ID = elem[0].split("=")[1];
            return ID;
        }
        return "0";
    }

    public static String get_Instrument(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            String instr = elem[1].split("=")[1];
            return instr;
        }
        return "0";
    }

    public static int get_Quantity(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            int quant = Integer.parseInt(elem[2].split("=")[1]);
            return quant;
        } 
        return 0;
    }

    public static String get_Market(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            String market = elem[3].split("=")[1];
            return market;
        } 
        return "0";
    }

    public static int get_Price(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            int price = Integer.parseInt(elem[4].split("=")[1]);
            return price;
        }
        return 0;
    }

    // this takes the data without the checksum and counts the ascii to come up with the checksum number
    private static long ascii_checksum(String data){
        long temp_sum = ascii_sum(data);
        long temp_checksum = 0;
        temp_checksum = temp_sum % 256;
        return temp_checksum;
    }

}