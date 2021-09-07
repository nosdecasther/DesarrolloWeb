package com.desarrolloweb.spring.app.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Audit implements Serializable {

	private static final long serialVersionUID = 238408908L;

	@Column(name = "usu_created", nullable = false, length = 50)
	private String usuCreated;

	@Column(name = "usu_modified", nullable = false, length = 50)
	private String usuModified;

	@Column(name = "ts_created", nullable = false)
	private Timestamp tsCreated;

	@Column(name = "ts_modified", nullable = false)
	private Timestamp tsModified;

	public Audit() {

	}

	public Audit(String user) {
		this.usuCreated = user;
		this.tsCreated = new Timestamp(System.currentTimeMillis());
		this.usuModified = user;
		this.tsModified = new Timestamp(System.currentTimeMillis());
	}

	public String getUsuCreated() {
		return usuCreated;
	}

	public void setUsuCreated(String usuCreated) {
		this.usuCreated = usuCreated;
	}

	public Timestamp getTsCreated() {
		return tsCreated;
	}

	public void setTsCreated(Timestamp tsCreated) {
		this.tsCreated = tsCreated;
	}

	public String getUsuModified() {
		return usuModified;
	}

	public void setUsuModified(String usuModified) {
		this.usuModified = usuModified;
	}

	public Timestamp getTsModified() {
		return tsModified;
	}

	public void setTsModified(Timestamp tsModified) {
		this.tsModified = tsModified;
	}

}
