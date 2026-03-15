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
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ItemService {

private final ItemRepo itemRepo;
private final UserProfileRepo userProfileRepo;

public ItemResponseDto createItem (CreateItemDto item)throws UserNotFoundException
{
return mapToResponseDto(itemRepo.save(mapFromCreateItem(item)));


}

public ItemTable findById(Long id){

   return itemRepo.findById(id).orElseThrow(()->new RuntimeException("item not found"));

}



public void deleteItem(Long id) throws UserNotFoundException{
   ItemTable item=itemRepo.findById(id).orElseThrow(()->new UserNotFoundException(id + "item not found"));
   itemRepo.delete(item);

}

   public ItemResponseDto updateItem (Long id,CreateItemDto newItem)throws UserNotFoundException{

      ItemTable item=itemRepo.findById(id).orElseThrow(()->new UserNotFoundException(id + "item not found"));


   item.setItemName(newItem.getItemName());
   item.setItemDesc(newItem.getItemDesc());
   item.setStatus(Status.valueOf((newItem.getStatus().toLowerCase())));
   item.setIsResolved( newItem.getIsResolved());
   return(mapToResponseDto(itemRepo.save(item)));

   }
   private ItemTable mapFromCreateItem (CreateItemDto item) throws UserNotFoundException
   {
      UserProfile user =userProfileRepo.findByregNo(item.getReporterRegNo()).orElseThrow(() -> new UserNotFoundException(item.getReporterRegNo() +" User not found"));


   return ItemTable.builder()
           .itemName(item.getItemName())
           .itemDesc(item.getItemDesc())
           .status(Status.valueOf(item.getStatus().toLowerCase(Locale.ROOT)))
           .isResolved(item.getIsResolved())
           .userProfile(user)
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
           .build();


}

}



