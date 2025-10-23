package vn.com.fortis.service;

import vn.com.fortis.domain.dto.request.product.CodPaymentRequestDto;
import vn.com.fortis.domain.dto.response.product.CodPaymentResponseDto;

public interface CodPaymentService {

  CodPaymentResponseDto processCodPayment(CodPaymentRequestDto request);
}
