package com.unipi.data.mining.backend.entities.neo4j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.driver.Value;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Neo4jUser {

    private String mongoId;
    @JsonProperty(value = "name")
    private String fullName;
    private String country;
    @JsonProperty(value = "picture")
    private String image;
    private FriendRequest friendRequest;

    public Neo4jUser() {
    }

    public Neo4jUser(String mongoId, String fullName, String country, String image) {
        this.mongoId = mongoId;
        this.fullName = fullName;
        this.country = country;
        this.image = image;
    }

    public Neo4jUser(Value value){
        //this.id = value.get("<id>").asLong();
        this.mongoId = value.get("mongoId").asString();
        this.fullName = value.get("name").asString();
        this.country = value.get("country").asString();
        this.image = value.get("picture").asString();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public FriendRequest getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    @Override
    public String toString() {
        return "Neo4jUser{" +
                "mongoId='" + mongoId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", country='" + country + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neo4jUser neo4jUser = (Neo4jUser) o;
        return mongoId.equals(neo4jUser.mongoId) && fullName.equals(neo4jUser.fullName) && Objects.equals(country, neo4jUser.country) && Objects.equals(image, neo4jUser.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mongoId, fullName, country, image);
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
}
