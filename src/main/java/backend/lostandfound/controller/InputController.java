package backend.lostandfound.controller;

import backend.lostandfound.dto.ItemDto.CreateItemDto;
import backend.lostandfound.dto.ItemDto.ItemResponseDto;
import backend.lostandfound.model.ItemTable;
import backend.lostandfound.model.Role;
import backend.lostandfound.model.UserProfile;
import backend.lostandfound.repo.ItemRepo;
import backend.lostandfound.repo.UserProfileRepo;
import backend.lostandfound.service.ItemService;
import backend.lostandfound.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
public class InputController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String sayHello(){
    return "Hello";
    }

    @PostMapping("/createItem")
    public HttpEntity<ItemResponseDto> createItem(@Valid @RequestBody CreateItemDto item){

        return new ResponseEntity<>(itemService.createItem(item),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ItemResponseDto> delete(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ItemResponseDto> update(@PathVariable Long id,@Valid @RequestBody CreateItemDto item){

return new ResponseEntity<>(itemService.updateItem(id, item),HttpStatus.OK);
    }

@GetMapping("/listall")
    public ResponseEntity<List<ItemResponseDto>> listall(){
        return new ResponseEntity<>(itemService.displayAll(),HttpStatus.OK);
}

@GetMapping("listallbypages")
    public ResponseEntity<Page<ItemResponseDto>> listbypages(@RequestParam int page,@RequestParam int size){
        return new ResponseEntity<>(itemService.getAllItemsByPages(page,size),HttpStatus.OK);
}

}
