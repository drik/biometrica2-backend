import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FingerprintTemplateService from './fingerprint-template.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IFingerprintTemplate, FingerprintTemplate } from '@/shared/model/fingerprint-template.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FingerprintTemplateUpdate',
  setup() {
    const fingerprintTemplateService = inject('fingerprintTemplateService', () => new FingerprintTemplateService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fingerprintTemplate: Ref<IFingerprintTemplate> = ref(new FingerprintTemplate());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveFingerprintTemplate = async fingerprintTemplateId => {
      try {
        const res = await fingerprintTemplateService().find(fingerprintTemplateId);
        fingerprintTemplate.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.fingerprintTemplateId) {
      retrieveFingerprintTemplate(route.params.fingerprintTemplateId);
    }

    const initRelationships = () => {};

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      templateVersion: {},
      templateData: {},
      originalImage: {},
      originalImageMime: {},
      originalImageExtension: {},
      fingerprint: {},
    };
    const v$ = useVuelidate(validationRules, fingerprintTemplate as any);
    v$.value.$validate();

    return {
      fingerprintTemplateService,
      alertService,
      fingerprintTemplate,
      previousState,
      isSaving,
      currentLanguage,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.fingerprintTemplate.id) {
        this.fingerprintTemplateService()
          .update(this.fingerprintTemplate)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('biometricaApp.fingerprintTemplate.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.fingerprintTemplateService()
          .create(this.fingerprintTemplate)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('biometricaApp.fingerprintTemplate.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    clearInputImage(field, fieldContentType, idInput): void {
      if (this.fingerprintTemplate && field && fieldContentType) {
        if (Object.prototype.hasOwnProperty.call(this.fingerprintTemplate, field)) {
          this.fingerprintTemplate[field] = null;
        }
        if (Object.prototype.hasOwnProperty.call(this.fingerprintTemplate, fieldContentType)) {
          this.fingerprintTemplate[fieldContentType] = null;
        }
        if (idInput) {
          (<any>this).$refs[idInput] = null;
        }
      }
    },
  },
});
