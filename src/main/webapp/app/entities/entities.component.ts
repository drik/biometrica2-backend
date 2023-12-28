import { defineComponent, provide } from 'vue';

import FingerprintService from './fingerprint/fingerprint.service';
import FingerprintTemplateService from './fingerprint-template/fingerprint-template.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('fingerprintService', () => new FingerprintService());
    provide('fingerprintTemplateService', () => new FingerprintTemplateService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
