package com.example.demo.pojo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class User implements Serializable{

	private static final long serialVersionUID = -4494493649160633201L;
	
	private String dzyid;
	private String dzycode;
	private String dzyname;
	private String sexid;
	private ArrayList<Hrbmgw> gwlist; 
	
	public User() {

	}
	
	public User(String dzyid, String dzycode, String dzyname, String sexid) {
		super();
		this.dzyid = dzyid;
		this.dzycode = dzycode;
		this.dzyname = dzyname;
		this.sexid = sexid;
	}


	public String getDzyid() {
		return dzyid;
	}
	public void setDzyid(String dzyid) {
		this.dzyid = dzyid;
	}
	public String getDzycode() {
		return dzycode;
	}
	public void setDzycode(String dzycode) {
		this.dzycode = dzycode;
	}
	public String getDzyname() {
		return dzyname;
	}
	public void setDzyname(String dzyname) {
		this.dzyname = dzyname;
	}
	public String getSexid() {
		return sexid;
	}
	public void setSexid(String sexid) {
		this.sexid = sexid;
	}
	
		
	public ArrayList<Hrbmgw> getGwlist() {
		return gwlist;
	}


	public void setGwlist(ArrayList<Hrbmgw> gwlist) {
		this.gwlist = gwlist;
	}


	public void showgnlist() {
		if (!(this.gwlist==null)) {
			System.out.println("gwlist");
			Iterator<Hrbmgw> iterator=  this.gwlist.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
				
			}
		}
		
	}
		
	@Override
	public String toString() {
		return "User [dzyid=" + dzyid + ", dzycode=" + dzycode + ", dzyname=" + dzyname + ", sexid=" + sexid
				+ ", gwlist=" + gwlist + "]";
	}




	
}
