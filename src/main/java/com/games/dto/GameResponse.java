package com.games.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GameResponse {
	private int id;
	private String un;

	private Map<Integer, Integer> status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public Map<Integer, Integer> getStatus() {
		return status;
	}

	public void setStatus(Map<Integer, Integer> status) {
		this.status = status;
	}

}
