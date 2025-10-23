package vn.com.fortis.service;

import vn.com.fortis.domain.dto.request.address.AddressRequestDto;
import vn.com.fortis.domain.dto.response.address.AddressResponseDto;

import java.util.List;

public interface AddressService {

    AddressResponseDto addAddress(String email, AddressRequestDto addressRequestDto);

    AddressResponseDto updateAddress(String email, Long id, AddressRequestDto addressRequestDto);

    AddressResponseDto getAddressById(Long id);

    List<AddressResponseDto> getAddressesByUserId(String email);

    void deleteAddress(String email, Long id);
}
