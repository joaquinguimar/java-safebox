package es.jguimar.safeboxAPI.mapper;

import es.jguimar.safeboxAPI.entity.SafeBox;
import es.jguimar.safeboxAPI.model.ContentDTO;
import es.jguimar.safeboxAPI.model.SafeboxDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-05T11:34:57+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class SafeboxMapperImpl implements SafeboxMapper {

    @Override
    public SafeboxDTO toSafeboxDTO(SafeBox source) {
        if ( source == null ) {
            return null;
        }

        SafeboxDTO safeboxDTO = new SafeboxDTO();

        safeboxDTO.setId( source.getId() );

        return safeboxDTO;
    }

    @Override
    public ContentDTO toContentDTO(SafeBox source) {
        if ( source == null ) {
            return null;
        }

        ContentDTO contentDTO = new ContentDTO();

        List<String> list = source.getItems();
        if ( list != null ) {
            contentDTO.setItems( new ArrayList<String>( list ) );
        }

        return contentDTO;
    }
}
