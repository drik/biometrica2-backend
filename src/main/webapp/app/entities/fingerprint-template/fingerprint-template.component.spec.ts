/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import FingerprintTemplate from './fingerprint-template.vue';
import FingerprintTemplateService from './fingerprint-template.service';
import AlertService from '@/shared/alert/alert.service';

type FingerprintTemplateComponentType = InstanceType<typeof FingerprintTemplate>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('FingerprintTemplate Management Component', () => {
    let fingerprintTemplateServiceStub: SinonStubbedInstance<FingerprintTemplateService>;
    let mountOptions: MountingOptions<FingerprintTemplateComponentType>['global'];

    beforeEach(() => {
      fingerprintTemplateServiceStub = sinon.createStubInstance<FingerprintTemplateService>(FingerprintTemplateService);
      fingerprintTemplateServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          fingerprintTemplateService: () => fingerprintTemplateServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fingerprintTemplateServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(FingerprintTemplate, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(fingerprintTemplateServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.fingerprintTemplates[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: FingerprintTemplateComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(FingerprintTemplate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        fingerprintTemplateServiceStub.retrieve.reset();
        fingerprintTemplateServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        fingerprintTemplateServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeFingerprintTemplate();
        await comp.$nextTick(); // clear components

        // THEN
        expect(fingerprintTemplateServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(fingerprintTemplateServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
