package com.quortexservice.quortex.model;

public class UserProgressLevel {

	private Integer level;
	private Long currentLevelTime;
	private Long currentTotalLevelTime;
	private Long totalSpendTimeByUser;
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Long getCurrentLevelTime() {
		return currentLevelTime;
	}
	public void setCurrentLevelTime(Long currentLevelTime) {
		this.currentLevelTime = currentLevelTime;
	}
	public Long getCurrentTotalLevelTime() {
		return currentTotalLevelTime;
	}
	public void setCurrentTotalLevelTime(Long currentTotalLevelTime) {
		this.currentTotalLevelTime = currentTotalLevelTime;
	}
	public Long getTotalSpendTimeByUser() {
		return totalSpendTimeByUser;
	}
	public void setTotalSpendTimeByUser(Long totalSpendTimeByUser) {
		this.totalSpendTimeByUser = totalSpendTimeByUser;
	}
	@Override
	public String toString() {
		return "UserProgressLevel [level=" + level + ", currentLevelTime=" + currentLevelTime
				+ ", currentTotalLevelTime=" + currentTotalLevelTime + ", totalSpendTimeByUser=" + totalSpendTimeByUser
				+ "]";
	}
	
}
