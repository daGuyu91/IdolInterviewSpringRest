package com.interview.idol.InterviewWithIdol.controller;

import com.interview.idol.InterviewWithIdol.model.User;
import com.interview.idol.InterviewWithIdol.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;

    private UserController(UserRepository repository) {
        this.userRepository = repository;
    }
    @PostMapping("/users")
    private User saveUser(@RequestBody User user) {

        return userRepository.save(user);
    }
    @PutMapping("/users/{id}")
    private User updateUser(@RequestBody User userDetails, @PathVariable Long id) throws IOException {

        FileWriter writer = new FileWriter(
                "src/main/resources/users.txt");


        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setContactNumber(userDetails.getContactNumber());

                    try {
                        writer.write(user.getFirstName()+"#"+user.getLastName()+"#"+user.getContactNumber());
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    return userRepository.save(userDetails);
                });
    }
    @GetMapping
    private List<User> getAllUsers() throws IOException {

        File file = new File(
                "src\\main\\resources\\users.txt");

        List<User> allUsers = new ArrayList<>();
        BufferedReader br
                = new BufferedReader(new FileReader(file));
        User user = new User();
        String record;
        while ((record = br.readLine()) != null) {

            // Print the string
            System.out.println(record);
            String[] arrOfStr = record.split("#");

            user.setFirstName(arrOfStr[0]);
            user.setLastName(arrOfStr[1]);
            user.setContactNumber(arrOfStr[2]);
            allUsers = Arrays.asList(user);

        }
     return allUsers;
    }
    @GetMapping
    private User getUser(@RequestBody User userDetails,@PathVariable Long id) throws IOException {

        User user = getAllUsers().stream().filter(user1 -> user1.getId().equals(id)).findAny().get();
        userRepository.findById(id);
        return user;
    }
    @DeleteMapping
    private void deleteUserFile(@RequestBody User user ,@PathVariable Long id) throws IOException {
        getAllUsers().stream().forEach(u->{
            try{
                 if(id.equals(u.getId())){
                     getAllUsers().remove(user);
                 }
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/main/resources/users.txt"));
            writer.write("");
            writer.flush();

            writer.write(u.getFirstName()+"#"+u.getLastName()+"#"+u.getContactNumber());
            writer.close();
             } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        userRepository.delete(user);
    }
}
