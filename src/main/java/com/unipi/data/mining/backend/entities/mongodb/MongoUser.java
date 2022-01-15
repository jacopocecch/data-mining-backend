package com.unipi.data.mining.backend.entities.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Document("user")
public class MongoUser {

    @MongoId
    private ObjectId id;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    @Field(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String gender;
    private String country;
    private String username;
    private String phone;
    private String email;
    private String password;
    @Field(name = "registration_date")
    private LocalDate registrationDate;
    @Field(name = "picture")
    private String image;
    private Survey survey;
    private int cluster;

    public MongoUser() {
    }

    public MongoUser(ObjectId id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String country, String username, String phone, String email, String password, LocalDate registrationDate, String image, Survey survey, int cluster) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.image = image;
        this.survey = survey;
        this.cluster = cluster;
    }

    public MongoUser(String firstName, String lastName, LocalDate dateOfBirth, String gender, String country, String username, String phone, String email, String password, LocalDate registrationDate, String image, Survey survey, int cluster) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.image = image;
        this.survey = survey;
        this.cluster = cluster;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public String toString() {
        return "UserMongodb{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", image='" + image + '\'' +
                ", survey=" + survey +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
}
