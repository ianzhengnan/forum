package com.bbs.dao;

import java.io.Serializable;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Page implements Serializable{
	
	private static int DEFAULT_PAGE_SIZE = 20;
	
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	private long start; //the poistion of first item in current page, start from 0
	
	private List data;  //data in current page
	
	private long totalCount; // total records
	
	/**
	 * constructor, just for empty page
	 */
	public Page(){
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	/**
	 * default constructor
	 * 
	 * @param start
	 * @param totalSize
	 * @param pageSize
	 * @param data
	 */
	public Page(long start, long totalSize, int pageSize, List data){
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}
	
	/**
	 * 取总记录数.
	 */
	public long getTotalCound(){
		return this.totalCount;
	}
	
	/**
	 * get total pages number
	 */
	public long getTotalPageCount(){
		if(totalCount % pageSize ==0){
			return totalCount / pageSize;
		}else{
			return totalCount / pageSize + 1;
		}
	}
	
	/** 
	 * get page size
	 */
	public int getPageSize(){
		return pageSize;
	}
	
	/**
	 * get data from current page
	 */
	public List getResult(){
		return data;
	}
	
	/**
	 * Get current page number
	 */
	public long getCurrentPageNo(){
		return start / pageSize + 1;
	}
	
	/**
	 * if the next page is exist
	 */
	public boolean isHasNextPage(){
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}
	
	/**
	 * if there is a previous page
	 */
	public boolean isHasPreviousPage(){
		return this.getCurrentPageNo() > 1;
	}
	
	/**
	 * Get the first data's position of any page
	 * 
	 * @see #getStartOfPage(int, int)
	 */
	protected static int getStartOfPage(int pageNo){
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * get the first list position of any page
	 * 
	 * @param pageNo start from 1
	 * @param pageSize 
	 * @return the first data of this page
	 */
	public static int getStartOfPage(int pageNo, int pageSize){
		return (pageNo - 1) * pageSize;
	}

}	

