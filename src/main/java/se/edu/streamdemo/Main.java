package se.edu.streamdemo;

import static java.util.stream.Collectors.toList;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        printWelcomeMessage();
        Datamanager dataManager = new Datamanager("./data/data.txt");
        // "C:\\Users\\dcaksh\\Desktop\\ip\\data\\data.txt" << absolute path. if another user runs this locally, the program will crash
        // instead, use relative path
        // "./data/data.txt" << relative path. "." is the current folder, then from there you look for a data folder which contains data.txt
        // always use relative path when refering to files in your codebase so which machine you use java will resolve accordingly

        // if you want to pre-load something from somewhere e.g. preload csv file of ceg mods on nusmods
        // --> need to package this data as a resource along with the jar, so that when someone runs the jar, the package will be unpacked and run tgt


        ArrayList<Task> tasksData = dataManager.loadData();

        System.out.println("Printing all data ...");
        printAllData(tasksData);

        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);

        //print deadlines in sorted order using streams
        printDeadlinesUsingStreams(tasksData);

        System.out.println("Total number of deadlines: " + countDeadlines(tasksData));

        System.out.println("");
        ArrayList<Task> filteredList = filteredList(tasksData, "11");
        printAllData(filteredList);

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

    public static void printAllData(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingStreams(ArrayList<Task> tasks) {
        System.out.println("Print deadlines using streams");

        //first take your arraylist and convert to stream
        //filter by deadlines
        //sorting using lambdas instead of TaskComparator which involves making a new TaskComparator object etc
        //print for each task
        tasks.stream()
                .filter((t) -> t instanceof Deadline)
                .sorted((t1,t2) -> t1.getDescription().compareToIgnoreCase(t2.getDescription()))
                .forEach(System.out::println);
    }

    //highlight method name, shiftF6 to change method name from printFilteredList to filteredList since it's no longer printing but returning a list
    public static ArrayList<Task> filteredList(ArrayList<Task> tasks, String filterString) {
        //convert to streams
        //call lambda to first extract whatever we need, then check if contains filteredString
        //print for each
        ArrayList<Task> filteredList = (ArrayList<Task>) tasks.stream()
                .filter((Task t) -> t.getDescription().contains(filterString))
                .collect(toList());
        return filteredList;
    }

}
