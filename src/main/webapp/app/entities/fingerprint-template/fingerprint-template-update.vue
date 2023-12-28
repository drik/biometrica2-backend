<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="biometricaApp.fingerprintTemplate.home.createOrEditLabel"
          data-cy="FingerprintTemplateCreateUpdateHeading"
          v-text="t$('biometricaApp.fingerprintTemplate.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="fingerprintTemplate.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="fingerprintTemplate.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('biometricaApp.fingerprintTemplate.templateVersion')"
              for="fingerprint-template-templateVersion"
            ></label>
            <input
              type="text"
              class="form-control"
              name="templateVersion"
              id="fingerprint-template-templateVersion"
              data-cy="templateVersion"
              :class="{ valid: !v$.templateVersion.$invalid, invalid: v$.templateVersion.$invalid }"
              v-model="v$.templateVersion.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('biometricaApp.fingerprintTemplate.templateData')"
              for="fingerprint-template-templateData"
            ></label>
            <div>
              <div v-if="fingerprintTemplate.templateData" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(fingerprintTemplate.templateDataContentType, fingerprintTemplate.templateData)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left"
                  >{{ fingerprintTemplate.templateDataContentType }}, {{ byteSize(fingerprintTemplate.templateData) }}</span
                >
                <button
                  type="button"
                  v-on:click="
                    fingerprintTemplate.templateData = null;
                    fingerprintTemplate.templateDataContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_templateData" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_templateData"
                id="file_templateData"
                style="display: none"
                data-cy="templateData"
                v-on:change="setFileData($event, fingerprintTemplate, 'templateData', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="templateData"
              id="fingerprint-template-templateData"
              data-cy="templateData"
              :class="{ valid: !v$.templateData.$invalid, invalid: v$.templateData.$invalid }"
              v-model="v$.templateData.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="templateDataContentType"
              id="fingerprint-template-templateDataContentType"
              v-model="fingerprintTemplate.templateDataContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('biometricaApp.fingerprintTemplate.originalImage')"
              for="fingerprint-template-originalImage"
            ></label>
            <div>
              <img
                v-bind:src="'data:' + fingerprintTemplate.originalImageContentType + ';base64,' + fingerprintTemplate.originalImage"
                style="max-height: 100px"
                v-if="fingerprintTemplate.originalImage"
                alt="fingerprintTemplate image"
              />
              <div v-if="fingerprintTemplate.originalImage" class="form-text text-danger clearfix">
                <span class="pull-left"
                  >{{ fingerprintTemplate.originalImageContentType }}, {{ byteSize(fingerprintTemplate.originalImage) }}</span
                >
                <button
                  type="button"
                  v-on:click="clearInputImage('originalImage', 'originalImageContentType', 'file_originalImage')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_originalImage" v-text="t$('entity.action.addimage')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_originalImage"
                id="file_originalImage"
                style="display: none"
                data-cy="originalImage"
                v-on:change="setFileData($event, fingerprintTemplate, 'originalImage', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="originalImage"
              id="fingerprint-template-originalImage"
              data-cy="originalImage"
              :class="{ valid: !v$.originalImage.$invalid, invalid: v$.originalImage.$invalid }"
              v-model="v$.originalImage.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="originalImageContentType"
              id="fingerprint-template-originalImageContentType"
              v-model="fingerprintTemplate.originalImageContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('biometricaApp.fingerprintTemplate.originalImageMime')"
              for="fingerprint-template-originalImageMime"
            ></label>
            <input
              type="text"
              class="form-control"
              name="originalImageMime"
              id="fingerprint-template-originalImageMime"
              data-cy="originalImageMime"
              :class="{ valid: !v$.originalImageMime.$invalid, invalid: v$.originalImageMime.$invalid }"
              v-model="v$.originalImageMime.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('biometricaApp.fingerprintTemplate.originalImageExtension')"
              for="fingerprint-template-originalImageExtension"
            ></label>
            <input
              type="text"
              class="form-control"
              name="originalImageExtension"
              id="fingerprint-template-originalImageExtension"
              data-cy="originalImageExtension"
              :class="{ valid: !v$.originalImageExtension.$invalid, invalid: v$.originalImageExtension.$invalid }"
              v-model="v$.originalImageExtension.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./fingerprint-template-update.component.ts"></script>
