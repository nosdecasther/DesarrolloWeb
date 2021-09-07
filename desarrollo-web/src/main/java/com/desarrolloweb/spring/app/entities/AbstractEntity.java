package com.desarrolloweb.spring.app.entities;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class AbstractEntity implements Serializable{
	
	private static final long serialVersionUID = 1263876128L;
	
	@JsonIgnore
	@Embedded
	private Audit audit;

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}
	
	

}
