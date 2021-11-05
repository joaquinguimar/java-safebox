package es.jguimar.safeboxAPI.mapper;

import es.jguimar.safeboxAPI.entity.SafeBox;
import es.jguimar.safeboxAPI.model.ContentDTO;
import es.jguimar.safeboxAPI.model.SafeboxDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SafeboxMapper {

    SafeboxDTO toSafeboxDTO(SafeBox source);

    ContentDTO toContentDTO(SafeBox source);

}