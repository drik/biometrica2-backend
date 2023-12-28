package com.zelix.biometrica.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.zelix.biometrica.domain.FingerprintTemplate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FingerprintTemplateDTO implements Serializable {

    private Long id;

    private String templateVersion;

    @Lob
    private byte[] templateData;

    private String templateDataContentType;

    @Lob
    private byte[] originalImage;

    private String originalImageContentType;
    private String originalImageMime;

    private String originalImageExtension;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(String templateVersion) {
        this.templateVersion = templateVersion;
    }

    public byte[] getTemplateData() {
        return templateData;
    }

    public void setTemplateData(byte[] templateData) {
        this.templateData = templateData;
    }

    public String getTemplateDataContentType() {
        return templateDataContentType;
    }

    public void setTemplateDataContentType(String templateDataContentType) {
        this.templateDataContentType = templateDataContentType;
    }

    public byte[] getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(byte[] originalImage) {
        this.originalImage = originalImage;
    }

    public String getOriginalImageContentType() {
        return originalImageContentType;
    }

    public void setOriginalImageContentType(String originalImageContentType) {
        this.originalImageContentType = originalImageContentType;
    }

    public String getOriginalImageMime() {
        return originalImageMime;
    }

    public void setOriginalImageMime(String originalImageMime) {
        this.originalImageMime = originalImageMime;
    }

    public String getOriginalImageExtension() {
        return originalImageExtension;
    }

    public void setOriginalImageExtension(String originalImageExtension) {
        this.originalImageExtension = originalImageExtension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FingerprintTemplateDTO)) {
            return false;
        }

        FingerprintTemplateDTO fingerprintTemplateDTO = (FingerprintTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fingerprintTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FingerprintTemplateDTO{" +
            "id=" + getId() +
            ", templateVersion='" + getTemplateVersion() + "'" +
            ", templateData='" + getTemplateData() + "'" +
            ", originalImage='" + getOriginalImage() + "'" +
            ", originalImageMime='" + getOriginalImageMime() + "'" +
            ", originalImageExtension='" + getOriginalImageExtension() + "'" +
            "}";
    }
}
