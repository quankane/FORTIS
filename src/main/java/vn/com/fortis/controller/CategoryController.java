package vn.com.fortis.controller;

import vn.com.fortis.base.ResponseUtil;
import vn.com.fortis.base.RestApiV1;
import vn.com.fortis.constant.SuccessMessage;
import vn.com.fortis.constant.UrlConstant;
import vn.com.fortis.domain.dto.pagination.PaginationRequestDto;
import vn.com.fortis.domain.dto.request.category.CategoryRequestDto;
import vn.com.fortis.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@Validated
@RequiredArgsConstructor
@Slf4j(topic = "CATEGORY-CONTROLLER")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @Operation(
            summary = "Thêm danh mục",
            description = "Dùng để admin thêm danh mục với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @PostMapping(UrlConstant.Category.ADD_CATEGORY)
    public ResponseEntity<?> addCategory (@Valid @RequestBody CategoryRequestDto categoryRequest){
        return ResponseUtil.success(
                SuccessMessage.Category.ADD_CATEGORY_SUCCESS,
                categoryService.addCategory(categoryRequest)
        );
    }

    @Operation(
            summary = "Lấy danh mục theo ID",
            description = "Dùng để admin lấy danh mục theo ID với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @GetMapping(UrlConstant.Category.GET_CATEGORY_BY_ID)
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId){
        return ResponseUtil.success(
                SuccessMessage.Category.GET_CATEGORY_SUCCESS,
                categoryService.getCategoryById(categoryId)
        );
    }

    @Operation(
            summary = "Lấy tất cả danh mục con để hiện trong lúc tạo ",
            description = "Dùng để front end lấy ra tất cả danh mục render ra UI"
    )
    @GetMapping(UrlConstant.Category.GET_ALL_SUB_CATEGORY)
    public ResponseEntity<?> getAllSubCategories(){
        return ResponseUtil.success(
                SuccessMessage.Category.GET_ALL_CATEGORY_SUCCESS,
                categoryService.getAllSubCategories()
        );
    }

    @Operation(
            summary = "Cập nhật danh mục",
            description = "Dùng để admin cập nhật danh mục theo ID và request form với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @PutMapping(UrlConstant.Category.UPDATE_CATEGORY)
    public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryRequestDto categoryRequest){
        return ResponseUtil.success(
                SuccessMessage.Category.UPDATE_CATEGORY_SUCCESS,
                categoryService.updateCategory(categoryId, categoryRequest)
        );
    }

    @Operation(
            summary = "Xóa danh mục theo Id",
            description = "Dùng để admin xóa danh mục theo ID với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @DeleteMapping(UrlConstant.Category.DELETE_CATEGORY)
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseUtil.success(
                HttpStatus.NO_CONTENT,
                SuccessMessage.Category.DELETE_CATEGORY_SUCCESS
        );
    }

    @Operation(
            summary = "Lấy tất cả danh mục",
            description = "Tùy chọn search theo keyword and sort theo name"
    )
    @GetMapping(UrlConstant.Category.SEARCH_CATEGORY_BY_NAME_AND_SORT_BY_KEYWORD)
    public ResponseEntity<?> searchCategoryByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PaginationRequestDto paginationRequest = new PaginationRequestDto(pageNum, pageSize);
        return ResponseUtil.success(
                SuccessMessage.Category.GET_CATEGORY_SUCCESS,
                categoryService.searchCategoryByKeywordAndSortByKeyword(keyword, paginationRequest));
    }

}
