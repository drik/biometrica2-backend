import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FingerprintService from './fingerprint.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import FingerprintTemplateService from '@/entities/fingerprint-template/fingerprint-template.service';
import { type IFingerprintTemplate } from '@/shared/model/fingerprint-template.model';
import { type IFingerprint, Fingerprint } from '@/shared/model/fingerprint.model';
import { FingerName } from '@/shared/model/enumerations/finger-name.model';
import { HandName } from '@/shared/model/enumerations/hand-name.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FingerprintUpdate',
  setup() {
    const fingerprintService = inject('fingerprintService', () => new FingerprintService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fingerprint: Ref<IFingerprint> = ref(new Fingerprint());

    const fingerprintTemplateService = inject('fingerprintTemplateService', () => new FingerprintTemplateService());

    const fingerprintTemplates: Ref<IFingerprintTemplate[]> = ref([]);
    const fingerNameValues: Ref<string[]> = ref(Object.keys(FingerName));
    const handNameValues: Ref<string[]> = ref(Object.keys(HandName));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveFingerprint = async fingerprintId => {
      try {
        const res = await fingerprintService().find(fingerprintId);
        fingerprint.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.fingerprintId) {
      retrieveFingerprint(route.params.fingerprintId);
    }

    const initRelationships = () => {
      fingerprintTemplateService()
        .retrieve()
        .then(res => {
          fingerprintTemplates.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      uuid: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      fingerName: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      handName: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      template: {},
    };
    const v$ = useVuelidate(validationRules, fingerprint as any);
    v$.value.$validate();

    return {
      fingerprintService,
      alertService,
      fingerprint,
      previousState,
      fingerNameValues,
      handNameValues,
      isSaving,
      currentLanguage,
      fingerprintTemplates,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.fingerprint.id) {
        this.fingerprintService()
          .update(this.fingerprint)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('biometricaApp.fingerprint.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.fingerprintService()
          .create(this.fingerprint)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('biometricaApp.fingerprint.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
