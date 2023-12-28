/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FingerprintTemplateUpdate from './fingerprint-template-update.vue';
import FingerprintTemplateService from './fingerprint-template.service';
import AlertService from '@/shared/alert/alert.service';

type FingerprintTemplateUpdateComponentType = InstanceType<typeof FingerprintTemplateUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fingerprintTemplateSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<FingerprintTemplateUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('FingerprintTemplate Management Update Component', () => {
    let comp: FingerprintTemplateUpdateComponentType;
    let fingerprintTemplateServiceStub: SinonStubbedInstance<FingerprintTemplateService>;

    beforeEach(() => {
      route = {};
      fingerprintTemplateServiceStub = sinon.createStubInstance<FingerprintTemplateService>(FingerprintTemplateService);
      fingerprintTemplateServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          fingerprintTemplateService: () => fingerprintTemplateServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(FingerprintTemplateUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.fingerprintTemplate = fingerprintTemplateSample;
        fingerprintTemplateServiceStub.update.resolves(fingerprintTemplateSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fingerprintTemplateServiceStub.update.calledWith(fingerprintTemplateSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        fingerprintTemplateServiceStub.create.resolves(entity);
        const wrapper = shallowMount(FingerprintTemplateUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.fingerprintTemplate = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fingerprintTemplateServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        fingerprintTemplateServiceStub.find.resolves(fingerprintTemplateSample);
        fingerprintTemplateServiceStub.retrieve.resolves([fingerprintTemplateSample]);

        // WHEN
        route = {
          params: {
            fingerprintTemplateId: '' + fingerprintTemplateSample.id,
          },
        };
        const wrapper = shallowMount(FingerprintTemplateUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.fingerprintTemplate).toMatchObject(fingerprintTemplateSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fingerprintTemplateServiceStub.find.resolves(fingerprintTemplateSample);
        const wrapper = shallowMount(FingerprintTemplateUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
