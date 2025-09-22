package vn.com.fortis.helper;

import vn.com.fortis.domain.dto.request.user.UpdateUserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PersonalInformationHelper {

    public UpdateUserRequestDto handleEmptyStrings(
            UpdateUserRequestDto personalInformation) {
        if (personalInformation == null) {
            return null;
        }

        if (personalInformation.getFirstName() != null && personalInformation.getFirstName().trim().isEmpty()) {
            personalInformation.setFirstName(null);
        }

        if (personalInformation.getLastName() != null && personalInformation.getLastName().trim().isEmpty()) {
            personalInformation.setLastName(null);
        }

        if (personalInformation.getPhone() != null && personalInformation.getPhone().trim().isEmpty()) {
            personalInformation.setPhone(null);
        }

        if (personalInformation.getNationality() != null && personalInformation.getNationality().trim().isEmpty()) {
            personalInformation.setNationality(null);
        }

        if(personalInformation.getEmail() != null && personalInformation.getEmail().trim().isEmpty()) {
            personalInformation.setEmail(null);
        }

//        if (personalInformation.getUpdateAddressRequestDto() != null) {
//            if (personalInformation.getUpdateAddressRequestDto().getCountry() != null &&
//                    personalInformation.getUpdateAddressRequestDto().getCountry().trim().isEmpty()) {
//                personalInformation.getUpdateAddressRequestDto().setCountry(null);
//            }
//
//            if (personalInformation.getUpdateAddressRequestDto().getCity() != null &&
//                    personalInformation.getUpdateAddressRequestDto().getCity().trim().isEmpty()) {
//                personalInformation.getUpdateAddressRequestDto().setCity(null);
//            }
//
//            if (personalInformation.getUpdateAddressRequestDto().getDistrict() == null &&
//                    personalInformation.getUpdateAddressRequestDto().getDistrict() == null) {
//                personalInformation.getUpdateAddressRequestDto().setDistrict(null);
//            }
//            if (personalInformation.getUpdateAddressRequestDto().getCommune() == null &&
//                    personalInformation.getUpdateAddressRequestDto().getCommune() == null) {
//                personalInformation.getUpdateAddressRequestDto().setCommune(null);
//            }
//            if (personalInformation.getUpdateAddressRequestDto().getDetailAddress() == null &&
//                    personalInformation.getUpdateAddressRequestDto().getDetailAddress() == null) {
//                personalInformation.getUpdateAddressRequestDto().setDetailAddress(null);
//            }
//            personalInformation.setUpdateAddressRequestDto(null);
//        }

        return personalInformation;
    }

}
