package hungpt.developer.planningpoker.entity;

public class Task {
	private int ID;
	private int precedence;
	private int time;
	private int experience;
	private int effort;
	private int priority;
	private int taskValue;


	public int getExperience() {
	    return  experience;
    }

    public int getEffort() {
	    return effort;
    }

    public int getPriority() {
	    return priority;
    }

    public int getTaskValue() {
	    return taskValue;
    }

    public double getScore() {
	    double timeEffort = 0.471 * time + 0.528 * effort;
	    double complexity = 0.478 * timeEffort + 0.522 * experience;
	    double importance = 0.518 * priority + 0.482 * taskValue;
	    return 0.494 * importance + 0.506 * complexity;
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
	 * @return the precedence
	 */
	public int getPrecedence() {
		return precedence;
	}

	/**
	 * @param precedence
	 *            the precedence to set
	 */
	public void setPrecedence(int precedence) {
		this.precedence = precedence;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	public void setExperience(int e) {
	    this.experience = e;
    }

    public void setValue(int e) {
        this.taskValue = e;
    }

    public void setEffort(int e) {
        this.effort = e;
    }

    public void setPriority(int e) {
        this.priority = e;
    }

	public String toString() {
		return "ID: " + ID + " precedence: " + precedence + " Time: " + time;
	}
}
