package backend.lostandfound.service;

import backend.lostandfound.Exception.DuplicateRegNoException;
import backend.lostandfound.Exception.SamePasswordException;
import backend.lostandfound.Exception.UserNotFoundException;
import backend.lostandfound.dto.UserDto.CreateUserDto;
import backend.lostandfound.dto.UserDto.UserResponseDto;
import backend.lostandfound.model.Role;
import backend.lostandfound.model.UserProfile;
import backend.lostandfound.repo.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserProfileRepo userProfileRepo;

    private  final PasswordEncoder passwordEncoder;

//for the sake of safety creating dto
    public UserResponseDto createUser(CreateUserDto userDto) {

        if (userProfileRepo.existsByRegNo(userDto.getRegNo())) {
            throw new DuplicateRegNoException(userDto.getRegNo() + "already exists ");
        }


        UserProfile savedUser = userProfileRepo.save(maptoCreateUser(userDto));
        return mapToResponse(savedUser);
    }
public UserResponseDto findUser(Long id) throws UserNotFoundException {
        UserProfile user= userProfileRepo.findById(id).orElseThrow(()->new UserNotFoundException(id+ "User not found"));
return mapToResponse(user);
}

public UserResponseDto findByRegNo(Long id) throws UserNotFoundException{
    UserProfile user= userProfileRepo.findByregNo(id).orElseThrow(()->new UserNotFoundException(id+"User not found"));
    return mapToResponse(user);

}

public void deleteUser(Long id) throws  UserNotFoundException{

           UserProfile user=userProfileRepo.findById(id).orElseThrow(()->new UserNotFoundException(id+ "User not found"));
             userProfileRepo.delete(user);






}

public List<UserResponseDto> listall(){
//        List<UserProfile> user=userProfileRepo.findAll();
//        List<UserResponseDto> responseUserProfile=new ArrayList<>();
//        user.forEach(
//                u->responseUserProfile.add(mapToResponse(u))
//
//
//        );
//        return responseUserProfile;
    //modern way to do the same thing
    return userProfileRepo.findAll().stream().map(this::mapToResponse).toList();
}
public UserResponseDto updatePassword(Long id,CreateUserDto newUser){
    UserProfile user=userProfileRepo.findById(id).orElseThrow(()->new UserNotFoundException(id+ "User not found"));


String old=user.getPassword();
if(passwordEncoder.matches( newUser.getPassword(),old))
      throw new SamePasswordException("Old password  and new password are same");
user.setPassword(passwordEncoder.encode(newUser.getPassword()));
return mapToResponse( userProfileRepo.save(user));
}

public UserResponseDto updateUser(Long id,CreateUserDto newUser){

    UserProfile user=userProfileRepo.findById(id).orElseThrow(()->new UserNotFoundException(id+ "User not found"));
    user.setName(newUser.getName());
    user.setEmail(newUser.getEmail());
    user.setStudyYear(newUser.getStudyYear());

return(mapToResponse(userProfileRepo.save(user)));

}
    private UserResponseDto mapToResponse(UserProfile user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .regNo(user.getRegNo())
                .studyYear(user.getStudyYear())
                .role(user.getRole())
                .build();
    }

    private UserProfile maptoCreateUser(CreateUserDto userDto){
        return UserProfile.builder()
                .regNo(userDto.getRegNo())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .studyYear(userDto.getStudyYear())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.student)
                .build();

    }

}


