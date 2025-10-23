package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.response.product.MediaResponseDto;
import vn.com.fortis.domain.entity.product.Media;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MediaMapper {

  MediaResponseDto mediaToMediaResponse(Media media);

}