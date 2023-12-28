package com.zelix.biometrica.service.mapper;

import com.zelix.biometrica.domain.Fingerprint;
import com.zelix.biometrica.domain.FingerprintTemplate;
import com.zelix.biometrica.service.dto.FingerprintDTO;
import com.zelix.biometrica.service.dto.FingerprintTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fingerprint} and its DTO {@link FingerprintDTO}.
 */
@Mapper(componentModel = "spring")
public interface FingerprintMapper extends EntityMapper<FingerprintDTO, Fingerprint> {
    @Mapping(target = "template", source = "template", qualifiedByName = "fingerprintTemplateId")
    FingerprintDTO toDto(Fingerprint s);

    @Named("fingerprintTemplateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FingerprintTemplateDTO toDtoFingerprintTemplateId(FingerprintTemplate fingerprintTemplate);
}
