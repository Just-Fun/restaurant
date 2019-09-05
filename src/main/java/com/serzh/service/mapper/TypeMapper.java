package com.serzh.service.mapper;

import com.serzh.domain.Type;
import com.serzh.service.dto.TypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    TypeDTO typeToTypeDTO(Type item);

    List<TypeDTO> typesToTypeDTOs(List<Type> items);

}
