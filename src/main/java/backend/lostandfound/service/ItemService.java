package backend.lostandfound.service;

import backend.lostandfound.Exception.UserNotFoundException;
import backend.lostandfound.dto.ItemDto.CreateItemDto;
import backend.lostandfound.dto.ItemDto.ItemResponseDto;
import backend.lostandfound.model.ItemTable;
import backend.lostandfound.model.Status;
import backend.lostandfound.model.UserProfile;
import backend.lostandfound.repo.ItemRepo;
import backend.lostandfound.repo.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import org.springframework.security.access.AccessDeniedException;
@Service
@RequiredArgsConstructor
public class ItemService {

private final ItemRepo itemRepo;
private final UserProfileRepo userProfileRepo;

public ItemResponseDto createItem (CreateItemDto item)throws UserNotFoundException
{
   item.setIsResolved(false);
return mapToResponseDto(itemRepo.save(mapFromCreateItem(item)));


}

public ItemTable findById(Long id){

   return itemRepo.findById(id).orElseThrow(()->new RuntimeException("item not found"));

}



public void deleteItem(Long id) throws UserNotFoundException{
   ItemTable item=itemRepo.findById(id).orElseThrow(()->new UserNotFoundException(id + "item not found"));
   itemRepo.delete(item);

}
   public void deleteItemforUsers(Long id) throws UserNotFoundException{
      ItemTable item=itemRepo.findById(id).orElseThrow(()->new UserNotFoundException(id + "item not found"));
      String username = SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getName();
      UserProfile owner = item.getUserProfile();
            if (!owner.getEmail().equals(username)) {
         throw new AccessDeniedException(
                 "You can delete only your own item"
         );
      }
      itemRepo.delete(item);

   }


   public ItemResponseDto updateItem (Long id,CreateItemDto newItem)throws UserNotFoundException{

      ItemTable item=itemRepo.findById(id).orElseThrow(()->new UserNotFoundException(id + "item not found"));


   item.setItemName(newItem.getItemName());
   item.setItemDesc(newItem.getItemDesc());
   item.setStatus(Status.valueOf((newItem.getStatus().toLowerCase())));
   item.setIsResolved( newItem.getIsResolved());
   if(newItem.getIsResolved())
      item.setResolvedAt(OffsetDateTime.now());
else item.setResolvedAt(null);

   return(mapToResponseDto(itemRepo.save(item)));

   }


   public ItemResponseDto updateItemforUsers (Long id,CreateItemDto newItem)throws UserNotFoundException{

      ItemTable item=itemRepo.findById(id).orElseThrow(()->new UserNotFoundException(id + "item not found"));

      String username = SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getName();
      UserProfile owner = item.getUserProfile();

      if (!owner.getEmail().equals(username)) {
         throw new AccessDeniedException(
                 "You can update only your own item"
         );
      }

      item.setItemName(newItem.getItemName());
      item.setItemDesc(newItem.getItemDesc());
      item.setStatus(Status.valueOf((newItem.getStatus().toLowerCase())));
      item.setIsResolved( newItem.getIsResolved());
      if(newItem.getIsResolved())
         item.setResolvedAt(OffsetDateTime.now());
      else item.setResolvedAt(null);

      return(mapToResponseDto(itemRepo.save(item)));

   }

   public List<ItemResponseDto> displayAll(){
   return itemRepo.findAll().stream().map(this::mapToResponseDto).toList();
   }

public Page<ItemResponseDto> getAllItemsByPages(int page,int size){
   Pageable pageable= PageRequest.of(page,size);
   Page<ItemTable> itemPage=itemRepo.findAll(pageable);
   return itemPage.map(this::mapToResponseDto)  ;

}
   private ItemTable mapFromCreateItem (CreateItemDto item) throws UserNotFoundException
   {
      String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
      UserProfile user =userProfileRepo.findByregNo(item.getReporterRegNo()).orElseThrow(() -> new UserNotFoundException(item.getReporterRegNo() +" User not found"));
      if (!user.getEmail().equals(currentUsername)) {
         throw new AccessDeniedException("You cannot report an item on behalf of another registration number. identity theft leads to termination of account");
      }

   return ItemTable.builder()
           .itemName(item.getItemName())
           .itemDesc(item.getItemDesc())
           .status(Status.valueOf(item.getStatus().toLowerCase(Locale.ROOT)))
           .isResolved(item.getIsResolved())
           .userProfile(user)
           .itemLocation(item.getLocation())
           .imageUrl(item.getImageUrl())
           .build();

}

private ItemResponseDto mapToResponseDto(ItemTable item){
   return ItemResponseDto.builder()
           .id(item.getId())
           .itemName(item.getItemName())
           .itemDesc(item.getItemDesc())
           .status(String.valueOf(item.getStatus()))
           .reporterRegNo(item.getUserProfile().getRegNo())
           .isResolved(item.getIsResolved())
           .reporterName(item.getUserProfile().getName())
           .reportedAt(item.getReportedAt())
           .resolvedAt(item.getResolvedAt())
           .location(item.getItemLocation())
           .imageUrl(item.getImageUrl())
           .build();


}

}



