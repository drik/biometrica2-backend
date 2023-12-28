package com.zelix.biometrica.service.dto;

import com.zelix.biometrica.domain.enumeration.FingerName;
import com.zelix.biometrica.domain.enumeration.HandName;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.zelix.biometrica.domain.Fingerprint} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FingerprintDTO implements Serializable {

    private Long id;

    @NotNull
    private UUID uuid;

    @NotNull
    private FingerName fingerName;

    @NotNull
    private HandName handName;

    private FingerprintTemplateDTO template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public FingerName getFingerName() {
        return fingerName;
    }

    public void setFingerName(FingerName fingerName) {
        this.fingerName = fingerName;
    }

    public HandName getHandName() {
        return handName;
    }

    public void setHandName(HandName handName) {
        this.handName = handName;
    }

    public FingerprintTemplateDTO getTemplate() {
        return template;
    }

    public void setTemplate(FingerprintTemplateDTO template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FingerprintDTO)) {
            return false;
        }

        FingerprintDTO fingerprintDTO = (FingerprintDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fingerprintDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FingerprintDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", fingerName='" + getFingerName() + "'" +
            ", handName='" + getHandName() + "'" +
            ", template=" + getTemplate() +
            "}";
    }
}
