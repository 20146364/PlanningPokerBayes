package hungpt.developer.planningpoker.entity;

import java.util.ArrayList;

public class Resource {
	private int ID;
	private String managementExperience; // Kinh nghiệm quản lí
	private int humanSkill; // Năng lực nhân viên
	private int productivity;// Năng suất
	private String timPressure; // Áp lực thời gian
	private ArrayList<Task> listTask; // danh sách các task phân bổ

	public Resource() {
		listTask = new ArrayList<Task>();
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	public String getManagementExperience() {
		return managementExperience;
	}

	public void setManagementExperience(String managementExperience) {
		this.managementExperience = managementExperience;
	}

	public int getHumanSkill() {
		return humanSkill;
	}

	public void setHumanSkill(int humanSkill) {
		this.humanSkill = humanSkill;
	}

	public int getProductivity() {
		return productivity;
	}

	public void setProductivity(int productivity) {
		this.productivity = productivity;
	}

	public String getTimPressure() {
		return timPressure;
	}

	public void setTimPressure(String timPressure) {
		this.timPressure = timPressure;
	}

	/**
	 * @return the listTask
	 */
	public ArrayList<Task> getListTask() {
		return listTask;
	}

	/**
	 * @param listTask
	 *            the listTask to set
	 */
	public void setListTask(ArrayList<Task> listTask) {
		this.listTask = listTask;
	}

	/**
	 * lấy ra tổng thời gian đã phân bổ cho tài nguyên
	 * 
	 * @return tổng thời gian đã phân bổ cho tài nguyên
	 */
	public int getLoaded() {
		int sum = 0;
		for (Task task : listTask) {
			sum += task.getTime();
		}
		return sum;
	}
}
