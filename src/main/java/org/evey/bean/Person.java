package org.evey.bean;

import org.evey.bean.BaseEntity;
import org.evey.bean.FileDetail;
import org.pos.coffee.bean.Address;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="PERSON")
public class Person extends BaseEntity {

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="MIDDLE_NAME")
	private String middleName;

	@Column(name="BIRTH_DATE")
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@ManyToOne
	@JoinColumn(name="ADDRESS", referencedColumnName = "ID")
	private Address address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMAGE", referencedColumnName = "ID")
	private FileDetail personImage;

	@Column(name = "IMAGE", insertable = false, updatable = false)
	private Long personImageId;

	private transient String completeName;

	private transient String fullName;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public FileDetail getPersonImage() {
		return personImage;
	}

	public void setPersonImage(FileDetail personImage) {
		this.personImage = personImage;
	}

	public Long getPersonImageId() {
		return personImageId;
	}

	public void setPersonImageId(Long personImageId) {
		this.personImageId = personImageId;
	}

	public String getFullName() {
		return this.lastName+", "+this.firstName;
	}

	public String getCompleteName(){
		return this.firstName+" "+this.lastName;
	}
	
}
