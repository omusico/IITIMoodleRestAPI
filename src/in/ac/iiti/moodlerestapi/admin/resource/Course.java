package in.ac.iiti.moodlerestapi.admin.resource;

import java.io.Serializable;

public class Course implements Serializable{
//	private int moodleCourseId;
    private String fullname;
    private String shortname;
    private int categoryid;
    private int startdate;
    private String semester; //1 0r 2
    private String year;     //1,2,3 or 4
    private Boolean publishedInMoodle;
    
    public Course(){
    	publishedInMoodle = false;
    } 
    
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public int getStartdate() {
		return startdate;
	}
	public void setStartdate(int startdate) {
		this.startdate = startdate;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Boolean getPublishedInMoodle() {
		return publishedInMoodle;
	}
	public void setPublishedInMoodle(Boolean publishedInMoodle) {
		this.publishedInMoodle = publishedInMoodle;
	}

    
        
}
