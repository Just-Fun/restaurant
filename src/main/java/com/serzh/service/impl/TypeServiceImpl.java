package com.serzh.service.impl;

import com.serzh.repository.TypeRepository;
import com.serzh.service.dto.TypeDTO;
import com.serzh.service.mapper.TypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TypeDTO> findAll() {
        return typeMapper.typesToTypeDTOs(typeRepository.findAll());
    }

}
