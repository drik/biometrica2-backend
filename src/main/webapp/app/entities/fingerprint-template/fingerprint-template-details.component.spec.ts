/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FingerprintTemplateDetails from './fingerprint-template-details.vue';
import FingerprintTemplateService from './fingerprint-template.service';
import AlertService from '@/shared/alert/alert.service';

type FingerprintTemplateDetailsComponentType = InstanceType<typeof FingerprintTemplateDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fingerprintTemplateSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('FingerprintTemplate Management Detail Component', () => {
    let fingerprintTemplateServiceStub: SinonStubbedInstance<FingerprintTemplateService>;
    let mountOptions: MountingOptions<FingerprintTemplateDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      fingerprintTemplateServiceStub = sinon.createStubInstance<FingerprintTemplateService>(FingerprintTemplateService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          fingerprintTemplateService: () => fingerprintTemplateServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fingerprintTemplateServiceStub.find.resolves(fingerprintTemplateSample);
        route = {
          params: {
            fingerprintTemplateId: '' + 123,
          },
        };
        const wrapper = shallowMount(FingerprintTemplateDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.fingerprintTemplate).toMatchObject(fingerprintTemplateSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fingerprintTemplateServiceStub.find.resolves(fingerprintTemplateSample);
        const wrapper = shallowMount(FingerprintTemplateDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
