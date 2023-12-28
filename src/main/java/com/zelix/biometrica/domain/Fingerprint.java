package com.zelix.biometrica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zelix.biometrica.domain.enumeration.FingerName;
import com.zelix.biometrica.domain.enumeration.HandName;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Fingerprint.
 */
@Entity
@Table(name = "fingerprint")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fingerprint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "finger_name", nullable = false)
    private FingerName fingerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hand_name", nullable = false)
    private HandName handName;

    @JsonIgnoreProperties(value = { "fingerprint" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private FingerprintTemplate template;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fingerprint id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Fingerprint uuid(UUID uuid) {
        this.setUuid(uuid);
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public FingerName getFingerName() {
        return this.fingerName;
    }

    public Fingerprint fingerName(FingerName fingerName) {
        this.setFingerName(fingerName);
        return this;
    }

    public void setFingerName(FingerName fingerName) {
        this.fingerName = fingerName;
    }

    public HandName getHandName() {
        return this.handName;
    }

    public Fingerprint handName(HandName handName) {
        this.setHandName(handName);
        return this;
    }

    public void setHandName(HandName handName) {
        this.handName = handName;
    }

    public FingerprintTemplate getTemplate() {
        return this.template;
    }

    public void setTemplate(FingerprintTemplate fingerprintTemplate) {
        this.template = fingerprintTemplate;
    }

    public Fingerprint template(FingerprintTemplate fingerprintTemplate) {
        this.setTemplate(fingerprintTemplate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fingerprint)) {
            return false;
        }
        return getId() != null && getId().equals(((Fingerprint) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fingerprint{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", fingerName='" + getFingerName() + "'" +
            ", handName='" + getHandName() + "'" +
            "}";
    }
}
