
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class QuizApp {
    private static final int TIME_LIMIT = 10; // seconds
    private static ArrayList<Question> quizQuestions = new ArrayList<>();
    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static Timer timer = new Timer();
    private static boolean timeUp = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Add quiz questions
        quizQuestions.add(new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Lisbon"}, 2));
        quizQuestions.add(new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 1));

        // Start the quiz
        for (currentQuestionIndex = 0; currentQuestionIndex < quizQuestions.size(); currentQuestionIndex++) {
            askQuestion(quizQuestions.get(currentQuestionIndex));
        }

        // Display the result
        displayResult();
    }

    private static void askQuestion(Question question) {
        System.out.println(question.question);
        for (int i = 0; i < question.options.length; i++) {
            System.out.println((i + 1) + ". " + question.options[i]);
        }

        timeUp = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
            }
        }, TIME_LIMIT * 1000);

        int answer = getAnswer();
        timer.cancel();
        timer = new Timer(); // reset timer

        if (answer == question.correctAnswer + 1) {
            score++;
        }
    }

    private static int getAnswer() {
        int answer = -1;
        while (!timeUp && (answer < 1 || answer > 4)) {
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt();
            } else {
                scanner.next(); // clear invalid input
            }
        }
        if (timeUp) {
            System.out.println("Time's up!");
        }
        return answer;
    }

    private static void displayResult() {
        System.out.println("Quiz Over!");
        System.out.println("Your Score: " + score + "/" + quizQuestions.size());
    }
}
