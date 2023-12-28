<template>
  <div>
    <h2 id="page-heading" data-cy="FingerprintHeading">
      <span v-text="t$('biometricaApp.fingerprint.home.title')" id="fingerprint-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('biometricaApp.fingerprint.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'FingerprintCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-fingerprint"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('biometricaApp.fingerprint.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fingerprints && fingerprints.length === 0">
      <span v-text="t$('biometricaApp.fingerprint.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="fingerprints && fingerprints.length > 0">
      <table class="table table-striped" aria-describedby="fingerprints">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprint.uuid')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprint.fingerName')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprint.handName')"></span></th>
            <th scope="row"><span v-text="t$('biometricaApp.fingerprint.template')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fingerprint in fingerprints" :key="fingerprint.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FingerprintView', params: { fingerprintId: fingerprint.id } }">{{ fingerprint.id }}</router-link>
            </td>
            <td>{{ fingerprint.uuid }}</td>
            <td v-text="t$('biometricaApp.FingerName.' + fingerprint.fingerName)"></td>
            <td v-text="t$('biometricaApp.HandName.' + fingerprint.handName)"></td>
            <td>
              <div v-if="fingerprint.template">
                <router-link :to="{ name: 'FingerprintTemplateView', params: { fingerprintTemplateId: fingerprint.template.id } }">{{
                  fingerprint.template.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FingerprintView', params: { fingerprintId: fingerprint.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FingerprintEdit', params: { fingerprintId: fingerprint.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fingerprint)"
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
          id="biometricaApp.fingerprint.delete.question"
          data-cy="fingerprintDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-fingerprint-heading" v-text="t$('biometricaApp.fingerprint.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-fingerprint"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeFingerprint()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./fingerprint.component.ts"></script>
