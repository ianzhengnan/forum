package com.bbs.domain;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_user")
public class User extends BaseDomain {

	
	public static final int USER_LOCK = 1;
	
	public static final int USER_UNLOCK = 0;

	public static final int FORUM_ADMIN = 2;
	
	public static final int NORMAL_USER = 1;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_type")
	private int userType = NORMAL_USER;
	
	@Column(name = "last_ip")
	private Date lastIp;
	
	@Column(name = "last_visit")
	private Date lastVisit;
	
	private String password;
	
	private int locked;
	
	private int credit;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(
			name = "t_board_manager", 
			joinColumns = {@JoinColumn(name = "user_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "board_id")})
	private Set<Board> manBoards = new HashSet<Board>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public Date getLastIp() {
		return lastIp;
	}

	public void setLastIp(Date lastIp) {
		this.lastIp = lastIp;
	}

	public Date getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Set<Board> getManBoards() {
		return manBoards;
	}

	public void setManBoards(Set<Board> manBoards) {
		this.manBoards = manBoards;
	}
	
}
