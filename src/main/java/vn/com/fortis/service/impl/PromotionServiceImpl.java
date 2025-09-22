package vn.com.fortis.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.com.fortis.constant.AppConstants;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.dto.pagination.PaginationCustom;
import vn.com.fortis.domain.dto.pagination.PaginationRequestDto;
import vn.com.fortis.domain.dto.pagination.PaginationResponseDto;
import vn.com.fortis.domain.dto.request.promotion.PromotionRequestDto;
import vn.com.fortis.domain.dto.response.promotion.PromotionResponseDto;
import vn.com.fortis.domain.entity.product.Promotion;
import vn.com.fortis.domain.mapper.PromotionMapper;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.PromotionRepository;
import vn.com.fortis.repository.criteria.SearchCriteria;
import vn.com.fortis.repository.criteria.SearchQueryCriteriaConsumer;
import vn.com.fortis.service.PromotionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CATEGORY-SERVICE")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromotionServiceImpl implements PromotionService {

    PromotionRepository promotionRepository;

    PromotionMapper promotionMapper;

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public PromotionResponseDto addPromotion(PromotionRequestDto requestDto) {
        if (promotionRepository.existsByPromotionCode(requestDto.getPromotionCode())) {
            throw new ResourceNotFoundException(ErrorMessage.Promotion.ERR_PROMOTION_EXISTED);
        }
        Promotion promotion = promotionMapper.promotionRequestDtoToPromotion(requestDto);
        return promotionMapper.promotionToPromotionResponseDto(promotionRepository.save(promotion));
    }

    @Override
    public PromotionResponseDto updatePromotion(Long id, PromotionRequestDto requestDto) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessage.Category.ERR_CATEGORY_NOT_EXISTED));
        promotionMapper.updatePromotionFromDto(requestDto, promotion);

        return promotionMapper.promotionToPromotionResponseDto(promotionRepository.save(promotion));
    }

    @Override
    public PromotionResponseDto getPromotionById(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessage.Promotion.ERR_PROMOTION_NOT_EXISTED));
        return promotionMapper.promotionToPromotionResponseDto(promotion);
    }

    @Override
    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorMessage.Promotion.ERR_PROMOTION_NOT_EXISTED));
        promotionRepository.delete(promotion);
    }

    @Override
    public PromotionResponseDto getPromotionByPromotionCode(String promotionCode) {
        Promotion promotion = promotionRepository.findByPromotionCode(promotionCode).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.Promotion.ERR_PROMOTION_NOT_EXISTED));
        return promotionMapper.promotionToPromotionResponseDto(promotion);
    }

    @Override
    public PaginationResponseDto<PromotionResponseDto> filterPromotions(PaginationRequestDto paginationRequest, String sortByPrice, String... search) {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        if (search != null) {
            if(search.length > 0) {
                Pattern pattern = Pattern.compile(AppConstants.SEARCH_OPERATOR);
                for (String s : search) {
                    Matcher matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        searchCriteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                    }
                }
            }
        }

        List<Promotion> promotions = getPromotions(paginationRequest.getPageNum(), paginationRequest.getPageSize(), searchCriteriaList,  sortByPrice);

        Long totalElements = getTotalElements(searchCriteriaList);

        log.info("total Element = {}", totalElements);

        Pageable pageable = PageRequest.of(paginationRequest.getPageNum(), paginationRequest.getPageSize());

        Page<Promotion> pages = new PageImpl<>(promotions, pageable, totalElements);

        PaginationCustom paginationCustom = PaginationCustom.builder()
                .pageNum(paginationRequest.getPageNum() + 1)
                .pageSize(paginationRequest.getPageSize())
                .totalElement(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .sortType(sortByPrice)
                .sortBy(sortByPrice != null ? "price" : null)
                .build();

        List<PromotionResponseDto> promotionResponseDtoList = pages.getContent().stream()
                .map(promotion -> promotionMapper.promotionToPromotionResponseDto(promotion))
                .toList();

        return PaginationResponseDto.<PromotionResponseDto>builder()
                .pageCustom(paginationCustom)
                .items(promotionResponseDtoList)
                .build();
    }

    private List<Promotion> getPromotions(int page, int size, List<SearchCriteria> searchCriteriaList, String sortByPrice) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Promotion> userCriteriaQuery = criteriaBuilder.createQuery(Promotion.class);
        Root<Promotion> userRoot = userCriteriaQuery.from(Promotion.class);

        Predicate promotionPredicate = criteriaBuilder.conjunction();
        SearchQueryCriteriaConsumer<Promotion> userSearchQueryCriteriaConsumer = new SearchQueryCriteriaConsumer(promotionPredicate, criteriaBuilder, userRoot);

        searchCriteriaList.forEach(userSearchQueryCriteriaConsumer);
        promotionPredicate = userSearchQueryCriteriaConsumer.getPredicate();

        userCriteriaQuery.where(promotionPredicate);

        if (sortByPrice != null) {
            if(sortByPrice.equalsIgnoreCase("asc")) {
                userCriteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get("discountPercent")));
            } else {
                userCriteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get("discountPercent")));
            }
        }

        return entityManager.createQuery(userCriteriaQuery)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    private Long getTotalElements(List<SearchCriteria> searchCriteriaList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Promotion> root = query.from(Promotion.class);

        Predicate predicate = criteriaBuilder.conjunction();
        SearchQueryCriteriaConsumer<Promotion> promotionSearchQueryCriteriaConsumer = new SearchQueryCriteriaConsumer(predicate, criteriaBuilder, root);

        searchCriteriaList.forEach(promotionSearchQueryCriteriaConsumer);
        predicate = promotionSearchQueryCriteriaConsumer.getPredicate();

        query.select(criteriaBuilder.count(root));
        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();
    }
}