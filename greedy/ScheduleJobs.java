package greedy;

import javafx.scene.layout.Priority;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ScheduleJobs {
    private class Job {
        private double weight;
        private double length;

        public Job (int w, int l) {
            weight = w;
            length = l;
        }

        public String toString() {
            return "Weight: " + weight+" ,Length: "+length;
        }
    }
    private ArrayList<Job> jobs;
    private int numJobs;

    public ScheduleJobs(String filename) throws IOException {
        String line;
        jobs = new ArrayList<Job>();

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while ((line = reader.readLine()) != null) {
            String[] strs = line.split(" ");
            if (strs.length  == 1) {
                numJobs = Integer.parseInt(strs[0]);
            }
            else if (strs.length == 2) {
                Job job = new Job(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
                jobs.add(job);
            }
        }
        reader.close();
    }

    private PriorityQueue<Job> getDiffScore() {
        PriorityQueue<Job> diffScore =
                new PriorityQueue<Job>(new Comparator<Job>() {
                    @Override
                    public int compare(Job o1, Job o2) {
                        double s1 = o1.weight - o1.length;
                        double s2 = o2.weight - o2.length;
                        if (s1 == s2) {
                            return (int) (o2.weight - o1.weight);
                        } else {
                            return (int) (s2 - s1);
                        }
                    }
                });
        diffScore.addAll(jobs);
        return diffScore;
    }

    public int greedyWithSub() {
        int sum = 0;
        int totalLength = 0;

        PriorityQueue<Job> diffScore = getDiffScore();
        while (!diffScore.isEmpty()) {
            Job j = diffScore.poll();
            totalLength += j.length;
            sum += j.weight*(totalLength);
        }
        return sum;
    }

    private PriorityQueue<Job> getRatioScore() {
        PriorityQueue<Job> ratioScore = new PriorityQueue<Job>(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                double s1 = o1.weight / o1.length;
                double s2 = o2.weight / o2.length;
                return Double.compare(s2, s1);
            }
        });
        ratioScore.addAll(jobs);
        return ratioScore;
    }

    public int greedyWtRatio() {
        int sum = 0;
        int totalLength = 0;

        PriorityQueue<Job> ratioScore = getRatioScore();
        while (!ratioScore.isEmpty()) {
            Job j = ratioScore.poll();
            totalLength += j.length;
            sum += j.weight*(totalLength);
        }
        return sum;
    }



    public void printFile() {
        for (int i = 0; i < numJobs; i++) {
            System.out.print(jobs.get(i));
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        ScheduleJobs scheJobs = new ScheduleJobs("./scheduling.txt");
        //scheJobs.printFile();
        //System.out.println(scheJobs.greedyWithSub());
        System.out.println(scheJobs.greedyWtRatio());
        //scheJobs.greedyWithSub();
    }

}

