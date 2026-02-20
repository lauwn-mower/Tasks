package se.edu.streamdemo;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        printWelcomeMessage();
        Datamanager dataManager = new Datamanager("./data/data.txt");
        ArrayList<Task> tasksData = dataManager.loadData();

//        System.out.println("Printing all data ...");
//        printAllData(tasksData);
//
//        printDataUsingStreams(tasksData);

        //interesting point: compare the results of printing using iteration and streams. you'll notice the order for printing using streams is flipped bcos of parallization
        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);
        printDeadlineUsingStreams(tasksData);

        System.out.println("Total number of deadlines (iteration): " + countDeadlines(tasksData));
        System.out.println("Total number of deadlines (streams): " + countDeadlinesUsingStreams(tasksData));


    }

    private static void printWelcomeMessage() {
        System.out.println("Welcome to Task manager (using streams)");
    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesUsingStreams(ArrayList<Task> tasks) {
        int count = (int) tasks.stream()
                .filter((Task t) -> t instanceof Deadline)
                .count();
        return count;
    }

    public static void printAllData(ArrayList<Task> tasksData) {
        System.out.println("Printing data using iteration...");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataUsingStreams(ArrayList<Task> tasks) {
        System.out.println("Printing data using streams...");

        //first thing to do is to convert the list to a stream
        // forEach() notice you need a consumer as an input. we use soutc instead of sout
        tasks.stream()
                .forEach(System.out::println);
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        System.out.println("Printing deadline using iteration...");
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlineUsingStreams(ArrayList<Task> tasks) {
        System.out.println("Printing deadline using streams...");
        // notice that filter() takes a predicate -> something that can take a true or false value. if you see printDeadlines(), it's "t instanceof Deadline"
        tasks.parallelStream()
                .filter( (Task t) -> t instanceof Deadline)
                .forEach(System.out::println);
    }

}
