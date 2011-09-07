package test;

import java.util.Date;

import com.wordpress.innerp.model.Model;

public class User extends Model{

	private String account;
	private String password;
	private Date reg;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getReg() {
		return reg;
	}
	public void setReg(Date reg) {
		this.reg = reg;
	}
	
	
}
