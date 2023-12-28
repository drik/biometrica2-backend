import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import FingerprintTemplateService from './fingerprint-template.service';
import { type IFingerprintTemplate } from '@/shared/model/fingerprint-template.model';
import useDataUtils from '@/shared/data/data-utils.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FingerprintTemplate',
  setup() {
    const { t: t$ } = useI18n();
    const dataUtils = useDataUtils();
    const fingerprintTemplateService = inject('fingerprintTemplateService', () => new FingerprintTemplateService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fingerprintTemplates: Ref<IFingerprintTemplate[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveFingerprintTemplates = async () => {
      isFetching.value = true;
      try {
        const res = await fingerprintTemplateService().retrieve();
        fingerprintTemplates.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveFingerprintTemplates();
    };

    onMounted(async () => {
      await retrieveFingerprintTemplates();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IFingerprintTemplate) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeFingerprintTemplate = async () => {
      try {
        await fingerprintTemplateService().delete(removeId.value);
        const message = t$('biometricaApp.fingerprintTemplate.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveFingerprintTemplates();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      fingerprintTemplates,
      handleSyncList,
      isFetching,
      retrieveFingerprintTemplates,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeFingerprintTemplate,
      t$,
      ...dataUtils,
    };
  },
});
