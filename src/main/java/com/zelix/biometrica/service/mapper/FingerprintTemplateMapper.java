package com.zelix.biometrica.service.mapper;

import com.zelix.biometrica.domain.FingerprintTemplate;
import com.zelix.biometrica.service.dto.FingerprintTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FingerprintTemplate} and its DTO {@link FingerprintTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface FingerprintTemplateMapper extends EntityMapper<FingerprintTemplateDTO, FingerprintTemplate> {}
