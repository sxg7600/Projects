package VISA;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
class VISA {
    Random rand = new Random();
    Scanner user_input_collection = new Scanner(System.in);
    ArrayList<String> random_question_number_storage = new ArrayList<>();
    int asked_question;
    VISA(){
        try{
            FileReader read_from_asked_question = new FileReader("Asked_Question.txt");
            BufferedReader bufferred_from_asked_question = new BufferedReader(read_from_asked_question);
            String string_of_asked_question;
            while((string_of_asked_question = bufferred_from_asked_question.readLine()) != null){
                this.asked_question = Integer.parseInt(string_of_asked_question);
            }
        }
        catch(IOException ignored){}
    }
    public void Interview() {
        String random_question_number;
        boolean asked_question = false;
        random_question_number = String.valueOf(rand.nextInt(43 - 1 + 1) + 1);
        try{
            BufferedReader q_r_buffer = new BufferedReader(new FileReader("Question_Recorder.txt"));
            String q_r_fetch;
            while((q_r_fetch = q_r_buffer.readLine()) != null){
                if(random_question_number.equals(q_r_fetch)){
                    asked_question = true;
                    break;
                }
            }
            q_r_buffer.close();
        }
        catch(IOException ignored){}
        if ((!asked_question))
        {
            this.asked_question++;
            String user_input = "q";
            boolean first_execution = true;
            if(this.asked_question == 1){
                System.out.println("""
                        
                        \tGood Morning Sajan,
                        \tWelcome To The First Section of Your Interview!
                        \tLets begin!!!""");
            }
            if(this.asked_question == 16){
                System.out.println("""
                                                
                        \tHello Sajan, Good Afternoon!
                        \tWelcome To The Second Section of Your Interview!
                        \tJust a Quick Remainder, you've completed 15 Interview Questions so far.
                        \tLets make the others count!!!""");
            }
            if(this.asked_question == 31){
                System.out.println("""
                        
                        \tGood Evening Sajan,
                        \tWelcome To The Final Section of Your Interview!
                        \tJust a Quick Remainder, you've completed 30 Interview Questions so far.
                        \tLets get the others done as well!!!""");
            }
            while (user_input.toLowerCase().charAt(0) != 'y' && user_input.toLowerCase().charAt(0) != 'n') {
                if (!first_execution) {
                    System.out.println("\t\tInvalid Insertion!");
                }
                System.out.print("\n\tPress 'Y' to continue and 'N' to discontinue...\n\t");
                user_input = user_input_collection.next();
                first_execution = false;
            }
            try{
                FileWriter fw_qr = new FileWriter("Question_Recorder.txt", true);
                fw_qr.write(random_question_number + "\n");
                fw_qr.close();
            }
            catch(IOException ignored){}
            if (user_input.toLowerCase().charAt(0) == 'y') {
                try {
                    FileReader reader = new FileReader("questions_set.txt");
                    BufferedReader b_r = new BufferedReader(reader);
                    String file_line;
                    boolean display_answer = false;
                    boolean similar_question_type = false;
                    boolean check_for_similar_question = false;
                    while ((file_line = b_r.readLine()) != null) {
                        if (similar_question_type) {
                            System.out.println(file_line);
                            similar_question_type = false;
                            continue;
                        }
                        if (check_for_similar_question) {
                            if (file_line.contains("OR")) {
                                System.out.println(file_line);
                                similar_question_type = true;
                            } else {
                                String ch = "B";
                                boolean loop_check = false;
                                while (ch.toLowerCase().charAt(0) != 'a') {
                                    if (!loop_check) {
                                        System.out.print("Press 'A' for answer: ");
                                        loop_check = true;
                                    } else {
                                        System.out.print("Invalid Character!\nPress A key for answer: ");
                                    }
                                    ch = user_input_collection.next();
                                }
                                int begin = 0;
                                int end = 120;
                                boolean last_execution = false;
                                boolean first_time = true;
                                while (!last_execution) {
                                    try {
                                        //System.out.println("Begin = " + begin + "End = " + end);
                                        if (end >= file_line.length()) {
                                            System.out.println(file_line.substring(begin, file_line.length()));
                                            last_execution = true;
                                        } else {
                                            if (file_line.charAt(end - 1) != ' ' && file_line.charAt(end) != ' ') {
                                                if (first_time) {
                                                    first_time = false;
                                                    end = ((file_line.substring(begin, end)).lastIndexOf(" ")) + 1;
                                                } else {
                                                    end = begin + ((file_line.substring(begin, end)).lastIndexOf(" ")) + 1;
                                                }
                                            } else {
                                                first_time = false;
                                            }
                                            System.out.println(file_line.substring(begin, end));
                                        }
                                        begin = end;
                                        if (file_line.charAt(begin) == ' ') {
                                            begin += 1;
                                        }
                                        end = Math.min(end + 120, file_line.length());
                                    } catch (IndexOutOfBoundsException ignored) {
                                    }
                                }
                                System.out.println("\n\tNo of asked questions: " + this.asked_question);
                                try {
                                    FileWriter fw2_1_1 = new FileWriter("Asked_Question.txt", true);
                                    fw2_1_1.write(String.valueOf("\n" + this.asked_question));
                                    fw2_1_1.close();
                                } catch (IOException ignored) {
                                }
                                if(this.asked_question == 15){
                                    System.out.println("\n\tBravo! You've Successfully Completed the First Section(15 Interview Questions).");
                                    return;
                                }
                                if(this.asked_question == 30){
                                    System.out.println("\n\tAmazing! You've Successfully Completed the Second Section(30 Interview Questions).");
                                    return;
                                }
                                break;
                            }
                        }
                        if (file_line.contains(random_question_number + ". ")) {
                            System.out.println(file_line);
                            check_for_similar_question = true;
                        }
                    }
                } catch (IOException ignored) {
                }
                Interview();
            }
            else {
                System.out.println("""
                        
                        
                        \t\tYou haven't completed your daily questions!
                        \t\tNow, You'll have to start again!""");
                try{
                    new FileWriter("Asked_Question.txt", false).close();
                    FileWriter pqr = new FileWriter("Asked_Question.txt");
                    pqr.write("0");
                    pqr.close();
                    new FileWriter("Question_Recorder.txt", false).close();
                }
                catch(IOException ignored){}
            }
        }
        else {
            if(this.asked_question == 43) {
                try {
                    new FileWriter("Asked_Question.txt", false).close();
                    FileWriter xyz = new FileWriter("Asked_Question.txt");
                    xyz.write("0");
                    xyz.close();
                    new FileWriter("Question_Recorder.txt", false).close();
                } catch (IOException ignored) {
                }
                System.out.println("\n\tHurray! You've completed all the interview questions! (43 Questions)");
                System.out.println("\n\n\t\tThank you for using this platform!\n\t\tWe hope you get your F-1 VISA soon!");
            }
            else{
                Interview();
            }
        }
    }
}
public class Interview{
    public static void main(String[] args){
        VISA interview = new VISA();
        interview.Interview();
    }
}