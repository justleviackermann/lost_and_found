package backend.lostandfound.service;

import backend.lostandfound.Exception.DuplicateRegNoException;
import backend.lostandfound.Exception.PasswordNotMatchException;
import backend.lostandfound.Exception.SamePasswordException;
import backend.lostandfound.Exception.UserNotFoundException;
import backend.lostandfound.dto.UserDto.CreateUserDto;
import backend.lostandfound.dto.UserDto.Login;
import backend.lostandfound.dto.UserDto.UserResponseDto;
import backend.lostandfound.model.Role;
import backend.lostandfound.model.UserProfile;
import backend.lostandfound.repo.UserProfileRepo;
import backend.lostandfound.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserProfileRepo userProfileRepo;

    private  final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

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

    public void deleteUserfoorUser(Long id) throws  UserNotFoundException,AccessDeniedException{

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        UserProfile user=userProfileRepo.findById(id).orElseThrow(()->new UserNotFoundException(id+ "User not found"));
        if(!user.getEmail().equals(username)){
            throw new AccessDeniedException(
                    "You can delete only your own profile"
            );
        }
        userProfileRepo.delete(user);






    }
public Map<String,String> loginser(Login login) throws PasswordNotMatchException{
UserProfile user=userProfileRepo.findByEmail(login.getEmail()).orElseThrow(()->new UserNotFoundException(login.getEmail()+ " not found"));
if(!passwordEncoder.matches(login.getPassword() , user.getPassword()))
throw new PasswordNotMatchException("Given password is incorrect");
String token= jwtUtils.generateToken(login.getEmail(), user.getRegNo(),user.getRole());
return Map.of("token",token);
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
    String username = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();
    if(!newUser.getEmail().equals(username)){
        throw new AccessDeniedException(
                "Please login to update your password or try to update only your password"
        );
    }

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

    public UserResponseDto updateUserforUser(Long id,CreateUserDto newUser){
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        if(!newUser.getEmail().equals(username)){
            throw new AccessDeniedException(
                    "You can delete only your own profile"
            );
        }
        UserProfile user=userProfileRepo.findById(id).orElseThrow(()->new UserNotFoundException(id+ "User not found"));
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setStudyYear(newUser.getStudyYear());

        return(mapToResponse(userProfileRepo.save(user)));

    }

    public Page<UserResponseDto> getAllUsersByPages(int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<UserProfile> userPage=userProfileRepo.findAll(pageable);
        return userPage.map(this::mapToResponse)  ;

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


