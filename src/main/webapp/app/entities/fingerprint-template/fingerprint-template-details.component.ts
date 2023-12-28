import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import FingerprintTemplateService from './fingerprint-template.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IFingerprintTemplate } from '@/shared/model/fingerprint-template.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FingerprintTemplateDetails',
  setup() {
    const fingerprintTemplateService = inject('fingerprintTemplateService', () => new FingerprintTemplateService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const fingerprintTemplate: Ref<IFingerprintTemplate> = ref({});

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

    return {
      alertService,
      fingerprintTemplate,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
