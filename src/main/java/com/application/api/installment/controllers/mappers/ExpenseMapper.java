package com.application.api.installment.controllers.mappers;

import com.application.api.installment.controllers.dto.ExpenseRequestDTO;
import com.application.api.installment.controllers.dto.ExpenseResponseDTO;
import com.application.api.installment.entities.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    Expense dtoToEntity(ExpenseRequestDTO dto);
    ExpenseResponseDTO entityToDto(Expense entity);
}
