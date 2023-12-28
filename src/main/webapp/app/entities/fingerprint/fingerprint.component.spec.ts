/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Fingerprint from './fingerprint.vue';
import FingerprintService from './fingerprint.service';
import AlertService from '@/shared/alert/alert.service';

type FingerprintComponentType = InstanceType<typeof Fingerprint>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Fingerprint Management Component', () => {
    let fingerprintServiceStub: SinonStubbedInstance<FingerprintService>;
    let mountOptions: MountingOptions<FingerprintComponentType>['global'];

    beforeEach(() => {
      fingerprintServiceStub = sinon.createStubInstance<FingerprintService>(FingerprintService);
      fingerprintServiceStub.retrieve.resolves({ headers: {} });

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
          fingerprintService: () => fingerprintServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fingerprintServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Fingerprint, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(fingerprintServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.fingerprints[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: FingerprintComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Fingerprint, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        fingerprintServiceStub.retrieve.reset();
        fingerprintServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        fingerprintServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeFingerprint();
        await comp.$nextTick(); // clear components

        // THEN
        expect(fingerprintServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(fingerprintServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
