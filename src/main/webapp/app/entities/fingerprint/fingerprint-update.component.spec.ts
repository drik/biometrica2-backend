/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FingerprintUpdate from './fingerprint-update.vue';
import FingerprintService from './fingerprint.service';
import AlertService from '@/shared/alert/alert.service';

import FingerprintTemplateService from '@/entities/fingerprint-template/fingerprint-template.service';

type FingerprintUpdateComponentType = InstanceType<typeof FingerprintUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fingerprintSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<FingerprintUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Fingerprint Management Update Component', () => {
    let comp: FingerprintUpdateComponentType;
    let fingerprintServiceStub: SinonStubbedInstance<FingerprintService>;

    beforeEach(() => {
      route = {};
      fingerprintServiceStub = sinon.createStubInstance<FingerprintService>(FingerprintService);
      fingerprintServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          fingerprintService: () => fingerprintServiceStub,
          fingerprintTemplateService: () =>
            sinon.createStubInstance<FingerprintTemplateService>(FingerprintTemplateService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(FingerprintUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.fingerprint = fingerprintSample;
        fingerprintServiceStub.update.resolves(fingerprintSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fingerprintServiceStub.update.calledWith(fingerprintSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        fingerprintServiceStub.create.resolves(entity);
        const wrapper = shallowMount(FingerprintUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.fingerprint = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fingerprintServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        fingerprintServiceStub.find.resolves(fingerprintSample);
        fingerprintServiceStub.retrieve.resolves([fingerprintSample]);

        // WHEN
        route = {
          params: {
            fingerprintId: '' + fingerprintSample.id,
          },
        };
        const wrapper = shallowMount(FingerprintUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.fingerprint).toMatchObject(fingerprintSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fingerprintServiceStub.find.resolves(fingerprintSample);
        const wrapper = shallowMount(FingerprintUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
