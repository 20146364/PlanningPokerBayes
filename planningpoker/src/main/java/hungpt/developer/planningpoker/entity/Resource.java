package hungpt.developer.planningpoker.entity;

import java.util.ArrayList;

public class Resource {
	private int ID;
	private String agileExperience; // Kinh nghiệm quản lí Agile
	private int agileSkill; // Kĩ năng agile
	private int skillLevel;// Mức độ kĩ năng công việc
	private String dailyMeeting; // Tham dự họp thường xuyên
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

	/**
	 * @return the agileExperience
	 */
	public String getAgileExperience() {
		return agileExperience;
	}

	/**
	 * @param agileExperience
	 *            the agileExperience to set
	 */
	public void setAgileExperience(String agileExperience) {
		this.agileExperience = agileExperience;
	}

	/**
	 * @return the agileSkill
	 */
	public int getAgileSkill() {
		return agileSkill;
	}

	/**
	 * @param agileSkill
	 *            the agileSkill to set
	 */
	public void setAgileSkill(int agileSkill) {
		this.agileSkill = agileSkill;
	}

	/**
	 * @return the skillLevel
	 */
	public int getSkillLevel() {
		return skillLevel;
	}

	/**
	 * @param skillLevel
	 *            the skillLevel to set
	 */
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	/**
	 * @return the dailyMeeting
	 */
	public String getDailyMeeting() {
		return dailyMeeting;
	}

	/**
	 * @param dailyMeeting
	 *            the dailyMeeting to set
	 */
	public void setDailyMeeting(String dailyMeeting) {
		this.dailyMeeting = dailyMeeting;
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

	public String toString() {
		return "ID: " + this.ID + " agileExperience: " + agileExperience + " agileSkill: " + agileSkill
				+ " skillLevel: " + skillLevel + " dailyMeeting: " + dailyMeeting;
	}
}
