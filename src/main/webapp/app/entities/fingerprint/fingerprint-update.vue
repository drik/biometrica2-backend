<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="biometricaApp.fingerprint.home.createOrEditLabel"
          data-cy="FingerprintCreateUpdateHeading"
          v-text="t$('biometricaApp.fingerprint.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="fingerprint.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="fingerprint.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('biometricaApp.fingerprint.uuid')" for="fingerprint-uuid"></label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="fingerprint-uuid"
              data-cy="uuid"
              :class="{ valid: !v$.uuid.$invalid, invalid: v$.uuid.$invalid }"
              v-model="v$.uuid.$model"
              required
            />
            <div v-if="v$.uuid.$anyDirty && v$.uuid.$invalid">
              <small class="form-text text-danger" v-for="error of v$.uuid.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('biometricaApp.fingerprint.fingerName')" for="fingerprint-fingerName"></label>
            <select
              class="form-control"
              name="fingerName"
              :class="{ valid: !v$.fingerName.$invalid, invalid: v$.fingerName.$invalid }"
              v-model="v$.fingerName.$model"
              id="fingerprint-fingerName"
              data-cy="fingerName"
              required
            >
              <option
                v-for="fingerName in fingerNameValues"
                :key="fingerName"
                v-bind:value="fingerName"
                v-bind:label="t$('biometricaApp.FingerName.' + fingerName)"
              >
                {{ fingerName }}
              </option>
            </select>
            <div v-if="v$.fingerName.$anyDirty && v$.fingerName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fingerName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('biometricaApp.fingerprint.handName')" for="fingerprint-handName"></label>
            <select
              class="form-control"
              name="handName"
              :class="{ valid: !v$.handName.$invalid, invalid: v$.handName.$invalid }"
              v-model="v$.handName.$model"
              id="fingerprint-handName"
              data-cy="handName"
              required
            >
              <option
                v-for="handName in handNameValues"
                :key="handName"
                v-bind:value="handName"
                v-bind:label="t$('biometricaApp.HandName.' + handName)"
              >
                {{ handName }}
              </option>
            </select>
            <div v-if="v$.handName.$anyDirty && v$.handName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.handName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('biometricaApp.fingerprint.template')" for="fingerprint-template"></label>
            <select class="form-control" id="fingerprint-template" data-cy="template" name="template" v-model="fingerprint.template">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  fingerprint.template && fingerprintTemplateOption.id === fingerprint.template.id
                    ? fingerprint.template
                    : fingerprintTemplateOption
                "
                v-for="fingerprintTemplateOption in fingerprintTemplates"
                :key="fingerprintTemplateOption.id"
              >
                {{ fingerprintTemplateOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./fingerprint-update.component.ts"></script>
