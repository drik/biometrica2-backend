import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import FingerprintService from './fingerprint.service';
import { type IFingerprint } from '@/shared/model/fingerprint.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Fingerprint',
  setup() {
    const { t: t$ } = useI18n();
    const fingerprintService = inject('fingerprintService', () => new FingerprintService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fingerprints: Ref<IFingerprint[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveFingerprints = async () => {
      isFetching.value = true;
      try {
        const res = await fingerprintService().retrieve();
        fingerprints.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveFingerprints();
    };

    onMounted(async () => {
      await retrieveFingerprints();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IFingerprint) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeFingerprint = async () => {
      try {
        await fingerprintService().delete(removeId.value);
        const message = t$('biometricaApp.fingerprint.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveFingerprints();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      fingerprints,
      handleSyncList,
      isFetching,
      retrieveFingerprints,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeFingerprint,
      t$,
    };
  },
});
