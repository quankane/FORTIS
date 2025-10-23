package vn.com.fortis.service;

import vn.com.fortis.domain.dto.request.product.momo.MomoIpnRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface MomoService {

    Map<String, String> createPaymentOrder(Long orderId) throws JsonProcessingException;

    boolean handleIpnCallback(MomoIpnRequestDto request);

    Map<String, String> handleRedirectCallback(Map<String, String> params);

}
