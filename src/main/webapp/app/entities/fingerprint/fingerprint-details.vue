<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fingerprint">
        <h2 class="jh-entity-heading" data-cy="fingerprintDetailsHeading">
          <span v-text="t$('biometricaApp.fingerprint.detail.title')"></span> {{ fingerprint.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('biometricaApp.fingerprint.uuid')"></span>
          </dt>
          <dd>
            <span>{{ fingerprint.uuid }}</span>
          </dd>
          <dt>
            <span v-text="t$('biometricaApp.fingerprint.fingerName')"></span>
          </dt>
          <dd>
            <span v-text="t$('biometricaApp.FingerName.' + fingerprint.fingerName)"></span>
          </dd>
          <dt>
            <span v-text="t$('biometricaApp.fingerprint.handName')"></span>
          </dt>
          <dd>
            <span v-text="t$('biometricaApp.HandName.' + fingerprint.handName)"></span>
          </dd>
          <dt>
            <span v-text="t$('biometricaApp.fingerprint.template')"></span>
          </dt>
          <dd>
            <div v-if="fingerprint.template">
              <router-link :to="{ name: 'FingerprintTemplateView', params: { fingerprintTemplateId: fingerprint.template.id } }">{{
                fingerprint.template.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="fingerprint.id"
          :to="{ name: 'FingerprintEdit', params: { fingerprintId: fingerprint.id } }"
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

<script lang="ts" src="./fingerprint-details.component.ts"></script>
