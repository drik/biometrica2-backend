<template>
  <div>
    <h2 id="page-heading" data-cy="FingerprintTemplateHeading">
      <span v-text="t$('biometricaApp.fingerprintTemplate.home.title')" id="fingerprint-template-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('biometricaApp.fingerprintTemplate.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'FingerprintTemplateCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-fingerprint-template"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('biometricaApp.fingerprintTemplate.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fingerprintTemplates && fingerprintTemplates.length === 0">
      <span v-text="t$('biometricaApp.fingerprintTemplate.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="fingerprintTemplates && fingerprintTemplates.length > 0">
      <table class="table table-striped" aria-describedby="fingerprintTemplates">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprintTemplate.templateVersion')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprintTemplate.templateData')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprintTemplate.originalImage')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprintTemplate.originalImageMime')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprintTemplate.originalImageExtension')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fingerprintTemplate in fingerprintTemplates" :key="fingerprintTemplate.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FingerprintTemplateView', params: { fingerprintTemplateId: fingerprintTemplate.id } }">{{
                fingerprintTemplate.id
              }}</router-link>
            </td>
            <td>{{ fingerprintTemplate.templateVersion }}</td>
            <td>
              <a
                v-if="fingerprintTemplate.templateData"
                v-on:click="openFile(fingerprintTemplate.templateDataContentType, fingerprintTemplate.templateData)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="fingerprintTemplate.templateData"
                >{{ fingerprintTemplate.templateDataContentType }}, {{ byteSize(fingerprintTemplate.templateData) }}</span
              >
            </td>
            <td>
              <a
                v-if="fingerprintTemplate.originalImage"
                v-on:click="openFile(fingerprintTemplate.originalImageContentType, fingerprintTemplate.originalImage)"
              >
                <img
                  v-bind:src="'data:' + fingerprintTemplate.originalImageContentType + ';base64,' + fingerprintTemplate.originalImage"
                  style="max-height: 30px"
                  alt="fingerprintTemplate image"
                />
              </a>
              <span v-if="fingerprintTemplate.originalImage"
                >{{ fingerprintTemplate.originalImageContentType }}, {{ byteSize(fingerprintTemplate.originalImage) }}</span
              >
            </td>
            <td>{{ fingerprintTemplate.originalImageMime }}</td>
            <td>{{ fingerprintTemplate.originalImageExtension }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FingerprintTemplateView', params: { fingerprintTemplateId: fingerprintTemplate.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'FingerprintTemplateEdit', params: { fingerprintTemplateId: fingerprintTemplate.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fingerprintTemplate)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="biometricaApp.fingerprintTemplate.delete.question"
          data-cy="fingerprintTemplateDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-fingerprintTemplate-heading"
          v-text="t$('biometricaApp.fingerprintTemplate.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-fingerprintTemplate"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeFingerprintTemplate()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./fingerprint-template.component.ts"></script>
