package test;

import java.util.Date;

import com.wordpress.innerp.model.Model;

public class Music extends Model{
	
	private String name;
	private long user;
	private String url;
	private Date upload;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getUser() {
		return user;
	}
	public void setUser(long user) {
		this.user = user;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getUpload() {
		return upload;
	}
	public void setUpload(Date upload) {
		this.upload = upload;
	}
	
}
