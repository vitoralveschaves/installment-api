package com.application.api.installment.controllers.mappers;

import com.application.api.installment.controllers.dto.RevenueRequestDTO;
import com.application.api.installment.controllers.dto.RevenueResponseDTO;
import com.application.api.installment.entities.Revenue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RevenueMapper {
    Revenue dtoToEntity(RevenueRequestDTO dto);
    RevenueResponseDTO entityToDto(Revenue entity);
}
