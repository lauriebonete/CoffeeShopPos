package org.pos.coffee.bean;

import javax.persistence.*;

@Entity
@Table(name="ADDRESS")
public class Address extends BaseEntity {

	@Column(name="HOUSE_NUMBER")
	private String houseNumber;

	@Column(name="STREET")
	private String street;

	@Column(name="COMMUNITY")
	private String community;

	@Column(name="MUNICIPALITY")
	private String municipality;

	@Column(name="CITY")
	private String city;

	@Column(name="REGION")
	private String region;

	@Column(name="ZIP_CODE")
	private Integer zipCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COUNTRY", referencedColumnName = "KEY_")
	private ReferenceLookUp country;

	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	public ReferenceLookUp getCountry() {
		return country;
	}
	public void setCountry(ReferenceLookUp country) {
		this.country = country;
	}
	
}
