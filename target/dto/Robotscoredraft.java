package org.openafp.ibm.cmod.dto;

public class Robotscoredraft {

    @DBField(value="UserId", nullable=false)
    private Long userid = null;
	
    @DBField(value="BrowserFingerPrint", nullable=false)
    private String browserfingerprint = null;
	
    @DBField(value="ScoreType", nullable=false)
    private Integer scoretype = null;
	
    @DBField(value="Score", nullable=false)
    private Float score = null;
	
    @DBField(value="ScoringPeriod", nullable=true)
    private String scoringperiod = null;
	

    public Long getUserid() {
        return userid;
    }
    
    public void setUserid(Long userid) {
        this.userid = userid;
    }
    public String getBrowserfingerprint() {
        return browserfingerprint;
    }
    
    public void setBrowserfingerprint(String browserfingerprint) {
        this.browserfingerprint = browserfingerprint;
    }
    public Integer getScoretype() {
        return scoretype;
    }
    
    public void setScoretype(Integer scoretype) {
        this.scoretype = scoretype;
    }
    public Float getScore() {
        return score;
    }
    
    public void setScore(Float score) {
        this.score = score;
    }
    public String getScoringperiod() {
        return scoringperiod;
    }
    
    public void setScoringperiod(String scoringperiod) {
        this.scoringperiod = scoringperiod;
    }
}