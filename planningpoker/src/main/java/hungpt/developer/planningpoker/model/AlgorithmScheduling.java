package hungpt.developer.planningpoker.model;

import com.bayesserver.inference.InconsistentEvidenceException;
import hungpt.developer.planningpoker.entity.Resource;
import hungpt.developer.planningpoker.entity.Task;
import hungpt.developer.planningpoker.entity.UserStory;
import hungpt.developer.planningpoker.util.FileManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmScheduling {

    /* biến lưu độ dài sprint */
    private int cap;

    /* Mảng lưu thông tin các task gán cho mỗi resource */
    private List<List<String>> schedule;

    /* Biến trạng thái có lập lịch được hay không */
    private boolean scheduled;

    /* Thông tin các resource */
    private List<Resource> resources;

    /* Thông tin các userstory */
    private List<UserStory> userStories;

    /* Biến tổng số resource */
    private int numResource;

    /* Biến tổng số task */
    private int totalTask;

    /* List lưu các task đã được phân */
    private List<Task> sheduledTask;

    public AlgorithmScheduling(List<UserStory> userStories, List<Resource> resources, int cap) {
        this.resources = resources;
        this.cap = cap;
        this.scheduled = true;
        numResource = resources.size();
        sheduledTask = new ArrayList<>();
        this.userStories = userStories;

        this.totalTask = caculateNumberTask(userStories);

        /*
         * Khởi tạo mảng lưu các task tương ứng với mỗi team sau khi lập lịch
         */
        schedule = new ArrayList<>();
        for (int i = 0; i < numResource; i++) {
            schedule.add(new ArrayList<>());
        }

    }

    /**
     * thuật toán lập lịch
     *
     * @return có lập lịch thành công ko
     */
    public boolean runAlgorithm() {

        int numUserStory = userStories.size();
        int[] arrStories = new int[numUserStory];

        for (int indexStory = 0; indexStory < numUserStory; indexStory++) {
            UserStory userStory = this.findUserStory(arrStories);
            System.out.println(userStory);

            if (userStory == null) {
                System.out.println("Khong tim duoc userstory phu hop.");
                this.scheduled = false;
                return false;
            }

            // System.out.println("userstory: " + userStory.getStoryId());
            List<Task> listTask = userStory.getListTask();
            int numTask = listTask.size();

            for (Resource resource : resources) {
                while (true) {
                    Task maxValueTask = null;
                    double maxValue = 0.0;
                    for (Task task : listTask) {
                        System.out.println("task score" + task.getScore());
                        Task precessorTask = findTaskById(task.getPrecedence(), listTask);
                        if (resource.getListTask().size() < (totalTask / resources.size())
                                &&
                                resource.getLoaded() + task.getTime() < this.cap &&
                                (sheduledTask.contains(precessorTask) || task.getPrecedence() == 0) &&
                                !sheduledTask.contains(task)) {
                            resource.getListTask().add(task);
                            ArrayList<Resource> tmpResources = new ArrayList<Resource>();
                            tmpResources.add(resource);
                            int taskSize = resource.getListTask().size();
                            BayesNet net = new BayesNet(tmpResources, taskSize);
                            try {
                                net.construct();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                double pro[][] = net.infer();
                                double maxProb = pro[0][taskSize - 1];
                                if (maxValue < maxProb) {
                                    maxValue = maxProb;
                                    maxValueTask = task;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            resource.getListTask().remove(taskSize - 1);
                        }
                    }
                    if (maxValueTask != null) {
                        resource.getListTask().add(maxValueTask);
                        System.out.println("resource num tasks" + resource.getListTask().size());
                        schedule.get(resource.getID() - 1).add(userStory.getStoryId() + "." + maxValueTask.getID());
                        sheduledTask.add(maxValueTask);
                    } else {
                        break;
                    }
                }
            }
        }


        for (int i = 0; i < resources.size(); i++) {
            Resource resource = resources.get(i);
            System.out.println("num task of resouce " + i + " " + resource.getListTask().size());
        }
        return true;
    }


    /**
     * lấy về lịch sau khi lập
     *
     * @return
     */
    public int[][] getScheduleWithTimeTask() {
        int[][] schedule_task = new int[resources.size()][totalTask];
        for (int i = 0; i < resources.size(); i++) {
            ArrayList<Task> listScheduled = resources.get(i).getListTask();
            int numTask = listScheduled.size();
            for (int j = 0; j < totalTask; j++) {
                if (j < numTask) {
                    System.out.println("time " + listScheduled.get(j).getTime());
                    schedule_task[i][j] = listScheduled.get(j).getTime();
                } else {
                    schedule_task[i][j] = 0;
                }
            }
        }
        return schedule_task;
    }

    /**
     * trả về có lập được lịch hay không
     *
     * @return có lập được lịch hay không
     */
    public boolean isExistSchedule() {
        return scheduled;
    }

    public List<List<String>> getScheduledWithNameTask() {
        return schedule;
    }

    private int caculateNumberTask(List<UserStory> listUserStory) {
        int sum = 0;
        for (UserStory userStory : listUserStory) {
            sum += userStory.getListTask().size();
        }
        return sum;
    }

    /**
     * Tìm userStory chưa được lập lịch có point cao nhất
     *
     * @param arrStories: mảng lưu thông tin các userstory đã được lập lịch
     * @return null nếu đã lập lịch hết, ngược lại trả về userstory có điểm cao
     * nhất
     */
    private UserStory findUserStory(int[] arrStories) {
        int point = 0;
        int indexMaxPoint = -1;
        int numStories = arrStories.length;
        for (int index = 0; index < numStories; index++) {
            UserStory userStory = userStories.get(index);
            if (arrStories[index] == 0 && userStory.getStoryPoint() > point) {
                point = userStory.getStoryPoint();
                indexMaxPoint = index;
            }
        }
        if (indexMaxPoint == -1) {
            return null;
        } else {
            arrStories[indexMaxPoint] = 1;
            return userStories.get(indexMaxPoint);
        }
    }

    /**
     * tìm Resource thích hợp nhất để phần công task
     *
     * @return: resource thích hợp
     */
    public Resource findResource() {

        Resource resource = null;
        int minResource = 99999999;

        // tìm resource đã phân bố ít việc nhất
        for (Resource r : resources) {
            if (minResource > r.getLoaded()) {
                minResource = r.getLoaded();
                resource = r;
            }
        }

        return resource;
    }

    /**
     * tìm Resource thích hợp nhất để phần công task
     *
     * @throws InconsistentEvidenceException
     * @throws IOException
     * @return: resource thích hợp
     */
    private Resource findResource(Task task) throws InconsistentEvidenceException, IOException {

        List<Resource> res = new ArrayList<Resource>(resources);

        /*
         * lấy ra tải của các nguồn lực trước khi phân, lấy xong giả sử gán task
         * cho nguồn lực đó, Tính xác suất thực hiện thành công task đó của các
         * resource, rồi chọn ra resource có tải nhỏ nhất nhưng xác suất thực
         * hiện cao nhất
         */
        int loadeds[] = new int[numResource];
        for (int i = 0; i < numResource; i++) {
            loadeds[i] = res.get(i).getLoaded();
            res.get(i).getListTask().add(task);

        }

        // xây dựng mạng bayes rồi
        // tính xác suất thực hiện task đó của từng nguồn lực
        BayesNet net = new BayesNet(res, totalTask);
        net.construct();
        double pro[][] = net.infer();
        double prob[] = new double[numResource];

        for (int i = 0; i < numResource; i++) {
            int size = res.get(i).getListTask().size();
            prob[i] = pro[i][size - 1];
            res.get(i).getListTask().remove(size - 1);
        }

        // tìm tải nhỏ nhất của các nguồn lực
        int minLoaded = this.findMinLoaded(loadeds);
        int index = this.findMax(prob, loadeds, minLoaded);
        return resources.get(index);
    }

    /**
     * Tìm vị trí của nguồn lực có tải nhỏ nhất và xác suất thực hiện cao nhất
     *
     * @param prob
     * @param loaded
     * @param minLoaded
     * @return
     */
    private int findMax(double prob[], int loaded[], int minLoaded) {
        double max = -1;
        int index = 0;

        for (int i = 0; i < prob.length; i++) {
            if (loaded[i] == minLoaded) {
                if (prob[i] > max) {
                    max = prob[i];
                    index = i;
                }
            }
        }
        return index;
    }

    private int findMinLoaded(int loaded[]) {
        int min = 99999999;
        for (int r : loaded) {
            if (min > r) {
                min = r;
            }
        }
        return min;
    }

    /**
     * chọn task có thời gian lớn nhất và không có điều kiện task ưu tiên hoặc
     * task ưu tiên đã thực hiện xong
     *
     * @return task thoả mãn
     */
    private Task findTask(List<Task> tasks) {
        Task task = new Task();
        for (Task t : tasks) {

            if (t.getTime() > task.getTime()) {
                // nếu task ko có task ưu tiên
                if (t.getPrecedence() == 0 && !this.sheduledTask.contains(t)) {
                    task = t;
                } else {
                    // nếu task có task ưu tiên đã thực hiện cũng có tiềm năng
                    Task parent = this.findTaskById(t.getPrecedence(), tasks);
                    if (this.sheduledTask.contains(parent) && !this.sheduledTask.contains(t)) {
                        task = t;
                    }
                }
            }
        }

        return task;
    }

    /**
     * tìm task theo ID
     *
     * @param id: id của task cần tìm kiếm
     * @return
     */
    private Task findTaskById(int id, List<Task> tasks) {
        for (Task task : tasks) {
            if (task.getID() == id) {
                return task;
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException, InconsistentEvidenceException {
        List<UserStory> listUserStories = FileManage.readStory("C:\\Users\\tuanl\\Desktop\\data\\data modifed\\sprint9.csv");
        List<Resource> listResource = FileManage.readTeams("resources.csv");
        AlgorithmScheduling algorithmScheduling = new AlgorithmScheduling(listUserStories, listResource, 85);
        if (algorithmScheduling.runAlgorithm()) {
            System.out.println("lap duoc lich");
        } else {
            System.out.println("ko lap lich duoc");
        }

    }

    /**
     * @return the scheduled
     */
    public boolean isScheduled() {
        return scheduled;
    }

}
