package com.bp.cbe.service;

import com.bp.cbe.domain.dto.LoanDTO;

public interface LoanService extends BaseService<LoanDTO, Long> {
    LoanDTO create(Long personId, LoanDTO data);
}
