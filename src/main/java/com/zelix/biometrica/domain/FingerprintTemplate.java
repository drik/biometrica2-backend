package com.zelix.biometrica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FingerprintTemplate.
 */
@Entity
@Table(name = "fingerprint_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FingerprintTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "template_version")
    private String templateVersion;

    @Lob
    @Column(name = "template_data")
    private byte[] templateData;

    @Column(name = "template_data_content_type")
    private String templateDataContentType;

    @Lob
    @Column(name = "original_image")
    private byte[] originalImage;

    @Column(name = "original_image_content_type")
    private String originalImageContentType;

    @Column(name = "original_image_mime")
    private String originalImageMime;

    @Column(name = "original_image_extension")
    private String originalImageExtension;

    @JsonIgnoreProperties(value = { "template" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "template")
    private Fingerprint fingerprint;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FingerprintTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateVersion() {
        return this.templateVersion;
    }

    public FingerprintTemplate templateVersion(String templateVersion) {
        this.setTemplateVersion(templateVersion);
        return this;
    }

    public void setTemplateVersion(String templateVersion) {
        this.templateVersion = templateVersion;
    }

    public byte[] getTemplateData() {
        return this.templateData;
    }

    public FingerprintTemplate templateData(byte[] templateData) {
        this.setTemplateData(templateData);
        return this;
    }

    public void setTemplateData(byte[] templateData) {
        this.templateData = templateData;
    }

    public String getTemplateDataContentType() {
        return this.templateDataContentType;
    }

    public FingerprintTemplate templateDataContentType(String templateDataContentType) {
        this.templateDataContentType = templateDataContentType;
        return this;
    }

    public void setTemplateDataContentType(String templateDataContentType) {
        this.templateDataContentType = templateDataContentType;
    }

    public byte[] getOriginalImage() {
        return this.originalImage;
    }

    public FingerprintTemplate originalImage(byte[] originalImage) {
        this.setOriginalImage(originalImage);
        return this;
    }

    public void setOriginalImage(byte[] originalImage) {
        this.originalImage = originalImage;
    }

    public String getOriginalImageContentType() {
        return this.originalImageContentType;
    }

    public FingerprintTemplate originalImageContentType(String originalImageContentType) {
        this.originalImageContentType = originalImageContentType;
        return this;
    }

    public void setOriginalImageContentType(String originalImageContentType) {
        this.originalImageContentType = originalImageContentType;
    }

    public String getOriginalImageMime() {
        return this.originalImageMime;
    }

    public FingerprintTemplate originalImageMime(String originalImageMime) {
        this.setOriginalImageMime(originalImageMime);
        return this;
    }

    public void setOriginalImageMime(String originalImageMime) {
        this.originalImageMime = originalImageMime;
    }

    public String getOriginalImageExtension() {
        return this.originalImageExtension;
    }

    public FingerprintTemplate originalImageExtension(String originalImageExtension) {
        this.setOriginalImageExtension(originalImageExtension);
        return this;
    }

    public void setOriginalImageExtension(String originalImageExtension) {
        this.originalImageExtension = originalImageExtension;
    }

    public Fingerprint getFingerprint() {
        return this.fingerprint;
    }

    public void setFingerprint(Fingerprint fingerprint) {
        if (this.fingerprint != null) {
            this.fingerprint.setTemplate(null);
        }
        if (fingerprint != null) {
            fingerprint.setTemplate(this);
        }
        this.fingerprint = fingerprint;
    }

    public FingerprintTemplate fingerprint(Fingerprint fingerprint) {
        this.setFingerprint(fingerprint);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FingerprintTemplate)) {
            return false;
        }
        return getId() != null && getId().equals(((FingerprintTemplate) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FingerprintTemplate{" +
            "id=" + getId() +
            ", templateVersion='" + getTemplateVersion() + "'" +
            ", templateData='" + getTemplateData() + "'" +
            ", templateDataContentType='" + getTemplateDataContentType() + "'" +
            ", originalImage='" + getOriginalImage() + "'" +
            ", originalImageContentType='" + getOriginalImageContentType() + "'" +
            ", originalImageMime='" + getOriginalImageMime() + "'" +
            ", originalImageExtension='" + getOriginalImageExtension() + "'" +
            "}";
    }
}
