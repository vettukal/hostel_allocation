package com.iiitd.hostel.backend;

/**
 * Created by vince on 4/27/2015.
 */

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by irani_r on 8/25/2014.
 */
@Entity
public class Student implements Comparable<Student> {

    @Id
    Long id;
    String name;
    String address;
    String city;
    String state;
    String gender;
    String degree;
    String degreeYear;
    String contactNumber;
    Date dateOfBirth;
    Integer pinCode;
    String rollNumber;
    String branch;
    @Index
    String emailId;
    @Index
    Double distance;
    @Index
    String roomType;
    @Index
    Integer floorType;

    public Boolean getIsApplied() {
        return isApplied;
    }

    public void setIsApplied(Boolean isApplied) {
        this.isApplied = isApplied;
    }

    public Boolean getIsClusterOpted() {
        return isClusterOpted;
    }

    public void setIsClusterOpted(Boolean isClusterOpted) {
        this.isClusterOpted = isClusterOpted;
    }

    @Index
    Boolean isApplied;
    Boolean isClusterOpted;





    public void setRoomType(String roomType)
    {
        this.roomType=roomType;
    }

    public String getRoomType()
    {
        return this.roomType;
    }

    public void setFloorType(Integer floorType)
    {
        this.floorType=floorType;
    }

    public Integer getFloorType()
    {
        return this.floorType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegreeYear() {
        return degreeYear;
    }

    public void setDegreeYear(String degreeYear) {
        this.degreeYear = degreeYear;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public int compareTo(Student o) {
        return (int) (o.distance - this.distance);
    }
}