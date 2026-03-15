package backend.lostandfound.controller;

import backend.lostandfound.dto.UserDto.CreateUserDto;
import backend.lostandfound.dto.UserDto.UserResponseDto;
import backend.lostandfound.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserDto userDto ){

        return new ResponseEntity<>(

                userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userResponseDto=userService.findUser(id);
         return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
        }

    @GetMapping("/reg/{id}")
    public ResponseEntity<UserResponseDto> getRegUser(@PathVariable Long id) {
        UserResponseDto userResponseDto=userService.findByRegNo(id);
        return new ResponseEntity<>(userResponseDto,HttpStatus.OK);

        }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){

            userService.deleteUser(id);


        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/listall")
    public ResponseEntity<List<UserResponseDto>> listAll(){
        return new ResponseEntity<>(userService.listall(),HttpStatus.OK);
    }
    @PatchMapping("updatepassword/{id}")
    public ResponseEntity<UserResponseDto> updatePassword(@PathVariable Long id,@Valid @RequestBody CreateUserDto user){
        return new ResponseEntity<>(userService.updatePassword(id,user),HttpStatus.OK);

    }
    @PatchMapping("updateUser/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,@Valid @RequestBody CreateUserDto user){
        return new ResponseEntity<>(userService.updateUser(id,user),HttpStatus.OK);

    }

}
