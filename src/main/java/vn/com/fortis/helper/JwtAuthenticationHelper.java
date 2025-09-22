package vn.com.fortis.helper;

import vn.com.fortis.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationHelper {

    public boolean handleSoftDeletedUser(
            UserDetails userDetails,
            HttpServletResponse response
    ) throws IOException {
        if (isUserSoftDeleted(userDetails)) {
            sendDeletedAccountResponse(response);
            return true;
        }
        return false;
    }

    public boolean isUserSoftDeleted(UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUser().getIsDeleted() != null &&
                    customUserDetails.getUser().getIsDeleted();
        }
        return false;
    }

    private void sendDeletedAccountResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //  401 Unauthorized
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Account has been deleted\",\"message\":\"Your account has been deleted. Please contact support to recover.\"}");
    }

}

