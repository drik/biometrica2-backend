import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import FingerprintService from './fingerprint.service';
import { type IFingerprint } from '@/shared/model/fingerprint.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FingerprintDetails',
  setup() {
    const fingerprintService = inject('fingerprintService', () => new FingerprintService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const fingerprint: Ref<IFingerprint> = ref({});

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

    return {
      alertService,
      fingerprint,

      previousState,
      t$: useI18n().t,
    };
  },
});
