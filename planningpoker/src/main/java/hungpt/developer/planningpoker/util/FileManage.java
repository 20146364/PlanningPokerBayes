package hungpt.developer.planningpoker.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.bayesserver.TopologicalSort;
import com.bayesserver.inference.InconsistentEvidenceException;

import au.com.bytecode.opencsv.CSVReader;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;
import  hungpt.developer.planningpoker.entity.Resource;
import  hungpt.developer.planningpoker.entity.Task;
import  hungpt.developer.planningpoker.entity.UserStory;
import  hungpt.developer.planningpoker.gui.schedule.FrameAgile;

public class FileManage {

    public static final String COLUMN_SEPARATOR = ";";

    /**
     * đọc dữ liệu các team từ file csv
     * 
     * @param teamFile:
     *            file lưu dữ liệu
     * @return: danh sách các team đọc từ file csv
     * @throws IOException
     */
    public static List<Resource> readTeams(String teamFile) throws IOException {
        ArrayList<Resource> resources = new ArrayList<>();
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        InputStream inputStream = classloader.getResourceAsStream(teamFile);
//        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(teamFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                Resource resource = new Resource();
                resource.setID(Integer.parseInt(line[0]));
                resource.setManagementExperience(line[1]);
                resource.setHumanSkill(Integer.parseInt(line[2]));
                resource.setProductivity(Integer.parseInt(line[3]));
                resource.setTimPressure(line[4]);
                resources.add(resource);
            }
        } finally {
            reader.close();
        }
        return resources;
    }

    /**
     * đọc dữ liệu các task từ file csv
     * 
     * @param taskFile:
     *            file lưu trữ thông tin các task
     * @return danh sách các task đọc từ file csv
     * @throws IOException
     */
    public static List<Task> readTask(String taskFile) throws IOException {
        CSVReader reader = null;
        List<Task> tasks = new ArrayList<Task>();
        try {
            reader = new CSVReader(new FileReader(taskFile));
            String[] line;
            line = reader.readNext();
            while (line != null) {
                if (line[0].contains(";")) {
                    line = line[0].split(";");
                }
                Task task = new Task();
                task.setID(Integer.parseInt(line[0]));
                task.setPrecedence(Integer.parseInt(line[1]));
                task.setTime(Integer.parseInt(line[2]));
                task.setExperience(Integer.parseInt((line[3])));
                task.setEffort(Integer.parseInt((line[4])));
                task.setPriority(Integer.parseInt((line[5])));
                task.setValue(Integer.parseInt((line[6])));
                tasks.add(task);
                line = reader.readNext();
            }
        } finally {
            if (reader != null)
                reader.close();
        }
        return tasks;
    }

    /**
     * lưu dữ liệu sau lập lịch ra file csv
     * 
     * @param fileName:
     *            tên file lưu dữ liệu
     * @param scheduleNameTask:
     *            mảng các task giao cho các resource
     * @param probability:
     *            mảng các xác suất thực hiện
     * @throws IOException
     * @throws InconsistentEvidenceException
     */
    public static void writeCSVFile(String fileName, List<List<String>> scheduleNameTask, double[][] probability)
            throws IOException, InconsistentEvidenceException {
        Writer writer = null;
        try {
            writer = new FileWriter(fileName);

            for (int i = 0; i < scheduleNameTask.size(); i++) {
                List<String> tasks = scheduleNameTask.get(i);
                StringBuilder task = new StringBuilder("Resource " + (i + 1) + FileManage.COLUMN_SEPARATOR);
                StringBuilder prob = new StringBuilder(FileManage.COLUMN_SEPARATOR);

                for (int j = 0; j < tasks.size(); j++) {
         
                    task.append("Task " + tasks.get(j) + FileManage.COLUMN_SEPARATOR);
                    prob.append(probability[i][j] * 100 + "%" + FileManage.COLUMN_SEPARATOR);

                }
                task.append("\n");
                prob.append("\n");
                System.out.println(prob.toString());
                writer.append(task);
                writer.append(prob);
                writer.append("\n");
            }
            writer.append("\n");
            writer.append("\n");
            writer.append(
                    "Total" + FileManage.COLUMN_SEPARATOR + (FrameAgile.caculateProbTotal(probability) * 100) + "%");

        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

//    public static void main(String[] args) throws IOException {
//        // Writer writer = null;
//        // int[][] taskId = { { 1, 2, 3 }, { 1, 2, 3 } };
//        // double[][] probability = { { 0.2, 0.3, 0.5 }, { 0.5, 0.4, 0.5 } };
//        // try {
//        // writer = new PrintWriter("test.csv");
//        //
//        // for (int i = 0; i < taskId.length; i++) {
//        // int[] tasks = taskId[i];
//        // StringBuilder task = new StringBuilder("Resource " + (i + 1) + ";");
//        // StringBuilder prob = new StringBuilder(";");
//        //
//        // for (int j = 0; j < tasks.length; j++) {
//        // if (tasks[j] <= 0) {
//        // break;
//        // }
//        // task.append("Task " + tasks[j] + ";");
//        // prob.append(String.valueOf(probability[i][j] * 100) + "%;");
//        // System.out.println(probability[i][j]);
//        // }
//        // task.append("\n");
//        // prob.append("\n");
//        // writer.append(task.toString());
//        // writer.append(prob.toString());
//        // writer.append("\n");
//        // }
//        // writer.append("\n");
//        // writer.append("\n");
//        // writer.append("Total;" + (0.5 * 100) + "%");
//        //
//        // } finally {
//        //
//        // writer.flush();
//        // writer.close();
//        // }
//
//        List<UserStory> list = FileManage.readStory("sprint9.csv");
//        for (UserStory userStory : list) {
//            System.out.println(userStory);
//        }
//
//    }


//    private static List<Task> topoSort(List<Task> tasks) {
//        List<Task> sortedTasks = new ArrayList<Task>();
//        int numTasks = tasks.size();
//        Digraph g = new Digraph(numTasks);
//        for (Task task : tasks) {
//            g.addEdge(task.getPrecedence(), task.getID());
//        }
//        Topological sort = new Topological(g);
//        Iterable<Integer> orders = sort.order();
//        for (int n : orders) {
//            if (n == 0) continue;
//            for (Task task : tasks) {
//                if (task.getID() == n) {
//                    sortedTasks.add(task);
//                }
//            }
//        }
//        return sortedTasks;
//    }

    /**
     * read userstory from file
     * @param location
     * @return
     * @throws IOException
     */
    public static List<UserStory> readStory(String location) throws IOException {
        List<UserStory> listStory = new ArrayList<>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(location));
            String[] line;
            line = reader.readNext();
      
            /*
             * đọc dòng đầu tiên ra thông tin userstory(id, point, number task)
             * sau đó đọc <number task> dòng thông tin của từng task đối với
             * userstory đó
             */
            while (line != null) {

                if (line[0].contains(FileManage.COLUMN_SEPARATOR)) {
                    line = line[0].split(FileManage.COLUMN_SEPARATOR);
                }
                if (line.length == 0) {
                    line = reader.readNext();
                    continue;
                }
                UserStory userStory = new UserStory();
                userStory.setStoryId("U" + line[0]);
                userStory.setStoryPoint(Integer.parseInt(line[1]));

                int numTask = Integer.parseInt(line[2]);
                List<Task> listTask = new ArrayList<>();
                for (int i = 0; i < numTask; i++) {
                    String[] lineTask = reader.readNext();
                    if (lineTask == null) {
                        break;
                    }

                    if (lineTask[0].contains(FileManage.COLUMN_SEPARATOR)) {
                        lineTask = lineTask[0].split(FileManage.COLUMN_SEPARATOR);
                    }

                    Task task = new Task();
                    task.setID(Integer.parseInt(lineTask[0]));
                    task.setPrecedence(Integer.parseInt(lineTask[1]));
                    task.setTime(Integer.parseInt(lineTask[2]));
                    task.setExperience(Integer.parseInt((lineTask[3])));
                    task.setEffort(Integer.parseInt((lineTask[4])));
                    task.setPriority(Integer.parseInt((lineTask[5])));
                    task.setValue(Integer.parseInt((lineTask[6])));
                    listTask.add(task);
                }
                userStory.setListTask(listTask);
                listStory.add(userStory);

                line = reader.readNext();
            }
        } finally {
            if (reader != null)
                reader.close();
        }
        return listStory;
    }
}
