package com.example.threadsafety.service;

import com.example.threadsafety.DAO.UserEntity;
import com.example.threadsafety.DTO.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // holding users count in service class doesn't make sense but it's just for demonstration :)
    public int numberOfCreatedUsers = 0;

    public int addUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        saveUserToDB(userEntity);
        return numberOfCreatedUsers;
    }

    private void saveUserToDB(UserEntity userEntity) {
        numberOfCreatedUsers++;
        System.out.println("Thread " + Thread.currentThread().getName() + ": user: " + userEntity.getUsername() + " saved to db");
    }

    public int addUserSync(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        saveUserToDBSync(userEntity);
        return numberOfCreatedUsers;
    }

    synchronized private void saveUserToDBSync(UserEntity userEntity) {
        numberOfCreatedUsers++;
        System.out.println("Thread " + Thread.currentThread().getName() + ": user saved to db");
    }
}
