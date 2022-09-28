package create_questionbank_and_ans;

/* Quiz program that will take questions, option and answer from admin user and save it to the question bank.
   then if any user want to give the quiz, random 5 questions will be shown to the user from the question bank.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class QuizProgram {

    public static void main(String[] args) throws IOException, ParseException {

        Scanner sc = new Scanner(System.in);
        System.out.println("1.Add Quiz\n2.Start Quiz");
        int input = sc.nextInt();
        if (input == 1){
            questionList();
        } else if (input == 2) {
            startQuiz();
        }
    }

    public static void questionList() throws IOException, ParseException {

        char ch = 'y';
        String filename = "./src/main/resources/questionbank.json";

        do {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader(filename));
            JSONObject quesBankObj = new JSONObject();

            Scanner sc = new Scanner(System.in);
            System.out.println("\nPlease add a question:");
            quesBankObj.put("Question" , sc.nextLine());
            System.out.println("Input option a:");
            quesBankObj.put("option a" , "a: " + sc.nextLine());
            System.out.println("Input option b:");
            quesBankObj.put("option b" , "b: " + sc.nextLine());
            System.out.println("Input option c:");
            quesBankObj.put("option c" , "c: " + sc.nextLine());
            System.out.println("Input option d:");
            quesBankObj.put("option d" , "d: " + sc.nextLine());
            System.out.println("Please input correct answer: ");
            quesBankObj.put("answer" , sc.next());

            JSONArray questionArray = (JSONArray) obj;
            questionArray.add(quesBankObj);
            System.out.println(questionArray.size()+"\n");

            FileWriter fileWrite = new FileWriter(filename);
            fileWrite.write(questionArray.toJSONString());
            fileWrite.flush();
            fileWrite.close();

            System.out.println(questionArray);
            System.out.println("\nDo you want to add more question?[y/n]");
            ch = sc.next().charAt(0);
        }
        while (ch != 'n');
    }

    public static void startQuiz() throws IOException, ParseException {

        int point = 0;
        String filename = "./src/main/resources/questionbank.json";

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(filename));
        JSONArray quizArray = (JSONArray) obj;

        for(int i = 0; i < 5 ; i++){

            int min = 0;
            int max = quizArray.size();
            int position = (int) (Math.random()*(max-min+1)+min);

            JSONObject quizObj = (JSONObject) quizArray.get(position);

            String questionName = (String) quizObj.get("Question");
            String a = (String) quizObj.get("option a");
            String b = (String) quizObj.get("option b");
            String c = (String) quizObj.get("option c");
            String d = (String) quizObj.get("option d");
            System.out.println(questionName);
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
            System.out.println(d);

            Scanner sc = new Scanner(System.in);
            System.out.println("\nGive your answer:");
            String givenAnswer = sc.next();
            String correctAnswer = (String) quizObj.get("answer");

            if (givenAnswer.equals(correctAnswer)){
                System.out.println("Correct Answer!\n");
                point++;
            }
            else {
                System.out.println("Wrong Answer!\n");
            }
        }
        System.out.println("\nYour total point " + point + " out of 5");
    }
}
