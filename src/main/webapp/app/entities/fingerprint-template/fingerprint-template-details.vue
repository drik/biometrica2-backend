<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fingerprintTemplate">
        <h2 class="jh-entity-heading" data-cy="fingerprintTemplateDetailsHeading">
          <span v-text="t$('biometricaApp.fingerprintTemplate.detail.title')"></span> {{ fingerprintTemplate.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('biometricaApp.fingerprintTemplate.templateVersion')"></span>
          </dt>
          <dd>
            <span>{{ fingerprintTemplate.templateVersion }}</span>
          </dd>
          <dt>
            <span v-text="t$('biometricaApp.fingerprintTemplate.templateData')"></span>
          </dt>
          <dd>
            <div v-if="fingerprintTemplate.templateData">
              <a
                v-on:click="openFile(fingerprintTemplate.templateDataContentType, fingerprintTemplate.templateData)"
                v-text="t$('entity.action.open')"
              ></a>
              {{ fingerprintTemplate.templateDataContentType }}, {{ byteSize(fingerprintTemplate.templateData) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('biometricaApp.fingerprintTemplate.originalImage')"></span>
          </dt>
          <dd>
            <div v-if="fingerprintTemplate.originalImage">
              <a v-on:click="openFile(fingerprintTemplate.originalImageContentType, fingerprintTemplate.originalImage)">
                <img
                  v-bind:src="'data:' + fingerprintTemplate.originalImageContentType + ';base64,' + fingerprintTemplate.originalImage"
                  style="max-width: 100%"
                  alt="fingerprintTemplate image"
                />
              </a>
              {{ fingerprintTemplate.originalImageContentType }}, {{ byteSize(fingerprintTemplate.originalImage) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('biometricaApp.fingerprintTemplate.originalImageMime')"></span>
          </dt>
          <dd>
            <span>{{ fingerprintTemplate.originalImageMime }}</span>
          </dd>
          <dt>
            <span v-text="t$('biometricaApp.fingerprintTemplate.originalImageExtension')"></span>
          </dt>
          <dd>
            <span>{{ fingerprintTemplate.originalImageExtension }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="fingerprintTemplate.id"
          :to="{ name: 'FingerprintTemplateEdit', params: { fingerprintTemplateId: fingerprintTemplate.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./fingerprint-template-details.component.ts"></script>
