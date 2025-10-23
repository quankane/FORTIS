package vn.com.fortis.service.impl;

import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.dto.request.address.AddressRequestDto;
import vn.com.fortis.domain.dto.response.address.AddressResponseDto;
import vn.com.fortis.domain.entity.address.Address;
import vn.com.fortis.domain.entity.user.User;
import vn.com.fortis.domain.mapper.AddressMapper;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.AddressRepository;
import vn.com.fortis.repository.UserRepository;
import vn.com.fortis.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressServiceImpl implements AddressService {

    AddressMapper addressMapper;

    AddressRepository addressRepository;

    UserRepository userRepository;


    @Override
    public AddressResponseDto addAddress(String email, AddressRequestDto addressRequestDto) {
        User user = userRepository.findByUsernameAndIsDeletedFalse(email).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        Address address = addressMapper.addressRequestDtoToAddress(addressRequestDto);

        user.getAddresses().add(address);
        address.setUser(user);

        return addressMapper.addressToAddressResponseDto(addressRepository.save(address));
    }

    @Override
    public AddressResponseDto updateAddress(String email, Long id, AddressRequestDto addressRequestDto) {
        Address address = addressRepository
                .findByIdAndUserUsernameAndIsDeletedFalse(id, email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Address.ERR_ADDRESS_NOT_FOUND));

        addressMapper.updateAddressFromDto(addressRequestDto, address);
        return addressMapper.addressToAddressResponseDto(addressRepository.save(address));
    }

    @Override
    public AddressResponseDto getAddressById(Long id) {
        Address address = addressRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.Address.ERR_ADDRESS_NOT_FOUND)
        );
        return addressMapper.addressToAddressResponseDto(address);
    }

    @Override
    public List<AddressResponseDto> getAddressesByUserId(String email) {
        User user = userRepository.findByUsernameAndIsDeletedFalse(email).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        List<Address> addresses = addressRepository.getAddressByUserId(user.getId());

        return addresses.stream().map(address -> addressMapper.addressToAddressResponseDto(address)).toList();
    }

    @Override
    public void deleteAddress(String email, Long id) {
        Address address = addressRepository
                .findByIdAndUserUsernameAndIsDeletedFalse(id, email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Address.ERR_ADDRESS_NOT_FOUND));

        address.setIsDeleted(true);
        addressRepository.save(address);
    }
}
