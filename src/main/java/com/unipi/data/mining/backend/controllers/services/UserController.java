package com.unipi.data.mining.backend.controllers.services;

import com.unipi.data.mining.backend.data.Login;
import com.unipi.data.mining.backend.dtos.*;
import com.unipi.data.mining.backend.entities.mongodb.MongoUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController extends ServiceController {

    @GetMapping("{id}")
    ResponseEntity<UserDto> getById(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.getMongoUserById(id)),
                HttpStatus.OK
        );
    }

    @Transactional
    @DeleteMapping("{id}")
    ResponseEntity<Object> deleteById(@PathVariable("id") String id) {

        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("register")
    ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        MongoUser mongoUser = mapper.userDtoToMongoUser(userDto);
        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.registerUser(mongoUser)),
                HttpStatus.OK
                );
    }

    @Transactional
    @PutMapping("update")
    ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {

        MongoUser mongoUser = mapper.userDtoToMongoUser(userDto);

        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.updateUser(mongoUser)),
                HttpStatus.OK
        );
    }

    @PostMapping("login")
    ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto) {

        Login login = mapper.loginDtoToLogin(loginDto);

        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.login(login)),
                HttpStatus.OK
        );
    }

    @GetMapping()
    ResponseEntity<List<UserDto>> getAllUsers() {

        return new ResponseEntity<>(
                mapper.mongoUsersToUsersDto(userService.getAllUsers()),
                HttpStatus.OK
        );
    }

    @PostMapping("similarities/{id}")
    ResponseEntity<Object> updateSimilarUsers(@PathVariable String id) {

        userService.updateSimilarUsers(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping("similarities/{id}")
    ResponseEntity<List<Neo4jUserDto>> getSimilarUsers(@PathVariable String id) {

        return new ResponseEntity<>(
                mapper.neo4jUsersToNeo4jUsersDto(userService.getSimilarUsers(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("similar_nearby/{id}")
    ResponseEntity<List<Neo4jUserDto>> getNearbySimilarUsers(@PathVariable String id) {

        return new ResponseEntity<>(
                mapper.neo4jUsersToNeo4jUsersDto(userService.getNearbySimilarUsers(id)),
                HttpStatus.OK
        );
    }

    @Transactional
    @PutMapping("similarities")
    ResponseEntity<Object> updateSimilarUsers(@RequestParam(value = "from") String fromUserId,
                                              @RequestParam(value = "to") String toUserId,
                                              @RequestParam(value = "weight") @Min(0) @Max(1) double weight) {

        userService.updateSimilarUsers(fromUserId, toUserId, weight);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("friend_requests")
    ResponseEntity<Object> addFriendRequest(@RequestParam(value = "from") String fromUserId,
                        @RequestParam(value = "to") String toUserId) {

        userService.addFriendRequest(fromUserId, toUserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("friend_requests")
    ResponseEntity<Object> deleteFriendRequest(@RequestParam(value = "from") String fromUserId,
                                            @RequestParam(value = "to") String toUserId) {

        userService.deleteFriendRequest(fromUserId, toUserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PutMapping("friend_requests")
    ResponseEntity<Object> updateFriendRequest(@RequestParam(value = "from") String fromUserId,
                                            @RequestParam(value = "to") String toUserId,
                                            @RequestParam(value = "status") @Min(0) @Max(1) int status) {

        userService.updateFriendRequest(fromUserId, toUserId, status);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("friend_requests/{id}")
    ResponseEntity<List<Neo4jUserDto>> getIncomingFriendRequests(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.neo4jUsersToNeo4jUsersDto(userService.getIncomingFriendRequests(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("friends/{id}")
    ResponseEntity<List<Neo4jUserDto>> getFriends(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.neo4jUsersToNeo4jUsersDto(userService.getFriends(id)),
                HttpStatus.OK
        );
    }


    @Transactional
    @PostMapping("populate_neo4j")
    ResponseEntity<Object> populateNeo4jDatabase() {

        userService.populateNeo4jDatabase();
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }


    @GetMapping("cluster_values/{id}")
    ResponseEntity<ClusterValuesDto> getUserClusterValues(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.clusterValuesToClusterValuesDto(userService.getUserClusterValues(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("cluster_cluster_values/{id}")
    ResponseEntity<ClusterValuesDto> getUserClusterClusterValues(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.clusterValuesToClusterValuesDto(userService.getUserClusterClusterValues(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("most_similar/{id}")
    ResponseEntity<UserDto> getMostSimilarUser(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.getMostSimilarUser(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("neo4j/{id}")
    ResponseEntity<Neo4jUserDto> getNeo4jUser(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.neo4jUserToNeo4jUserDto(userService.getNeo4jUserByMongoId(id)),
                HttpStatus.OK
        );
    }
 }
