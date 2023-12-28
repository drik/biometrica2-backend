/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FingerprintDetails from './fingerprint-details.vue';
import FingerprintService from './fingerprint.service';
import AlertService from '@/shared/alert/alert.service';

type FingerprintDetailsComponentType = InstanceType<typeof FingerprintDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fingerprintSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Fingerprint Management Detail Component', () => {
    let fingerprintServiceStub: SinonStubbedInstance<FingerprintService>;
    let mountOptions: MountingOptions<FingerprintDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      fingerprintServiceStub = sinon.createStubInstance<FingerprintService>(FingerprintService);

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
          fingerprintService: () => fingerprintServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fingerprintServiceStub.find.resolves(fingerprintSample);
        route = {
          params: {
            fingerprintId: '' + 123,
          },
        };
        const wrapper = shallowMount(FingerprintDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.fingerprint).toMatchObject(fingerprintSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fingerprintServiceStub.find.resolves(fingerprintSample);
        const wrapper = shallowMount(FingerprintDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
