package vn.com.fortis.service;

import vn.com.fortis.domain.dto.pagination.PaginationRequestDto;
import vn.com.fortis.domain.dto.pagination.PaginationResponseDto;
import vn.com.fortis.domain.dto.response.invoice.InvoiceResponseDto;
import vn.com.fortis.domain.dto.response.product.OrderResponseDto;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface OrderService {
    InvoiceResponseDto getInvoiceDetails(Long orderId);

    PaginationResponseDto<OrderResponseDto> getAllOrders(PaginationRequestDto paginationRequest, String status);

    OrderResponseDto getOrderById(Long id);

    OrderResponseDto updateStatusOrderById(Long id, String status);

    byte[] generateInvoicePdf(Long orderId) throws DocumentException, IOException;
}
