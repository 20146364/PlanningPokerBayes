package hungpt.developer.planningpoker.entity;

import java.util.ArrayList;
import java.util.List;

public class UserStory {
    private String storyId;
    private int storyPoint;
    private List<Task> listTask;
    public UserStory() {
        listTask = new ArrayList<>();
    }
    public String getStoryId() {
        return storyId;
    }
    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public int getStoryPoint() {
        return storyPoint;
    }
    public void setStoryPoint(int storyPoint) {
        this.storyPoint = storyPoint;
    }

    public List<Task> getListTask() {
        return listTask;
    }

    public void setListTask(List<Task> listTask) {
        this.listTask = listTask;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(storyId);
        sb.append(",  ");
        sb.append(storyPoint);
        sb.append(",  ");
        sb.append(listTask.size());
        sb.append("\n");
        for (Task task: listTask){
            sb.append(task.getID());
            sb.append(",  ");
            sb.append(task.getPrecedence());
            sb.append(",  ");
            sb.append(task.getTime());
            sb.append("\n");
        }
        return sb.toString();
    }
}
