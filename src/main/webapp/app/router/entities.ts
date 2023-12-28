import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Fingerprint = () => import('@/entities/fingerprint/fingerprint.vue');
const FingerprintUpdate = () => import('@/entities/fingerprint/fingerprint-update.vue');
const FingerprintDetails = () => import('@/entities/fingerprint/fingerprint-details.vue');

const FingerprintTemplate = () => import('@/entities/fingerprint-template/fingerprint-template.vue');
const FingerprintTemplateUpdate = () => import('@/entities/fingerprint-template/fingerprint-template-update.vue');
const FingerprintTemplateDetails = () => import('@/entities/fingerprint-template/fingerprint-template-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'fingerprint',
      name: 'Fingerprint',
      component: Fingerprint,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fingerprint/new',
      name: 'FingerprintCreate',
      component: FingerprintUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fingerprint/:fingerprintId/edit',
      name: 'FingerprintEdit',
      component: FingerprintUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fingerprint/:fingerprintId/view',
      name: 'FingerprintView',
      component: FingerprintDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fingerprint-template',
      name: 'FingerprintTemplate',
      component: FingerprintTemplate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fingerprint-template/new',
      name: 'FingerprintTemplateCreate',
      component: FingerprintTemplateUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fingerprint-template/:fingerprintTemplateId/edit',
      name: 'FingerprintTemplateEdit',
      component: FingerprintTemplateUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fingerprint-template/:fingerprintTemplateId/view',
      name: 'FingerprintTemplateView',
      component: FingerprintTemplateDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
